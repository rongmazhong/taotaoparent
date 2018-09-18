package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈用户控制层〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/12
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkUserData(@PathVariable String param, @PathVariable Integer type) {
        TaotaoResult taotaoResult = userService.checkData(param, type);
        return taotaoResult;
    }

    /**
     * register 用户注册
     *
     * @param user 用户注册
     * @return TaotaoResult
     * @author MZRong
     * @date 2018/9/12 10:43
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletResponse response, HttpServletRequest request) {
        TaotaoResult login = userService.login(username, password);
        if (login.getStatus().equals(200)) {
            // 把token写入cookie
            CookieUtils.setCookie(request, response,TOKEN_KEY,login.getData().toString());
        }
        return login;
    }
    /*@RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET,
            // 指定返回响应数据的Content-type
             produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback) {
        TaotaoResult user = userService.getUserByToken(token);
        // 判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            return callback + "(" + JsonUtils.objectToJson(user) + ");";
        }
        return JsonUtils.objectToJson(user);
    }*/

    /**
     * jsonp的第二种方法，spring4.1以上版本使用
     */
    @RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback) {
        TaotaoResult user = userService.getUserByToken(token);
        // 判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return user;
    }

    @RequestMapping(value = "/user/logout/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult logout(String token) {
        TaotaoResult logout = userService.logout(token);
        return logout;
    }
}
