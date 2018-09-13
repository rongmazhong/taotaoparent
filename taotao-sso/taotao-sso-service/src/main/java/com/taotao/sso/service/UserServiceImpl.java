package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 〈用户处理接口实现类service〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/11
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_SESSION}")
    private String USER_SESSION;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String data, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        // 设置查询
        // 1.判断用户名是否可用、2手机、3email
        if (type == 1) {
            criteria.andUsernameEqualTo(data);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(data);
        } else if (type == 3) {
            criteria.andEmailEqualTo(data);
        } else {
            return TaotaoResult.build(400, "参数包含非法数据");
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            //查询到数据，返回false
            return TaotaoResult.ok(false);
        }
        //数据可以使用
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult register(TbUser user) {
        // 检查数据有效性
        if (StringUtils.isEmpty(user.getUsername())) {
            return TaotaoResult.build(400, "用户名不能为空");
        }
        // 判断用户名不重复
        if (!(boolean) checkData(user.getUsername(), 1).getData()) {
            return TaotaoResult.build(400, "用户名不能重复");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return TaotaoResult.build(400, "密码不能为空");
        }
        if (StringUtils.isEmpty(user.getPhone())) {
            return TaotaoResult.build(400, "电话号码不能为空");
        }
        // 判断电话不重复
        if (!(boolean) checkData(user.getPhone(), 2).getData()) {
            return TaotaoResult.build(400, "电话不能重复");
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            return TaotaoResult.build(400, "Email不能为空");
        }
        // 判断Email不重复
        if (!(boolean) checkData(user.getEmail(), 3).getData()) {
            return TaotaoResult.build(400, "Email不能重复");
        }
        // 补全pojo属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        // 密码md5加密
        String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5);
        // 插入数据
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password) {
        // 判断用户名和密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if (tbUsers == null && tbUsers.size() == 0) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        TbUser user = tbUsers.get(0);
        // 密码进行md5加密校验
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        // 生成token
        String token = UUID.randomUUID().toString();
        // 把用户信息保存到redis，key是token，value是用户信息
        // 清除密码
        user.setPassword(null);
        jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
        // 设置过期时间
        jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
        // 返回登陆成功，把token返回
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if (StringUtils.isEmpty(json)) {
            return TaotaoResult.build(400, "登陆过期");
        }
        //重置session的过期时间
        jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(tbUser);
    }

    @Override
    public TaotaoResult logout(String token) {
        Long del = jedisClient.del(USER_SESSION + ":" + token);
        return TaotaoResult.ok();
    }
}
