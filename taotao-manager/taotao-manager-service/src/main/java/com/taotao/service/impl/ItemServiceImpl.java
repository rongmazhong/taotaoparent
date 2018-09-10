package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.swing.*;
import java.util.Date;
import java.util.List;

/**
 * 〈itemService服务〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/4
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource(name = "itemAddtopic")
    private Destination destination;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ITEM_INFO}")
    private String itemInfo;

    @Value("${SECONDS}")
    private int seconds;

    @Override
    public TbItem getItemById(Long itemId) {
        // 查询数据库前查询缓存
        String json = jedisClient.get(itemInfo + ":" + itemId + ":BASE");
        try {
            if (StringUtils.isNotEmpty(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 缓存无查数据库
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        try {
            // 把查询结果添加到缓存
            jedisClient.set(itemInfo + ":" + itemId + ":BASE", JsonUtils.objectToJson(item));
            // 设置缓存过期时间，提高缓存的利用率
            jedisClient.expire(itemInfo + ":" + itemId+":BASE",seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(int start, int size) {
        //设置分页
        PageHelper.startPage(start, size);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        // 取查询结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc) {
        // 生成商品id
        Long itemId = IDUtils.genItemId();
        // 补全item的属性
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 向商品表插入数据
        itemMapper.insert(item);
        // 创建商品描述表对应的pojo
        TbItemDesc itemDesc = new TbItemDesc();
        // 补全pojo属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        // 向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        this.sendMsg(itemId);
        //返回结果
        return TaotaoResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        // 查询数据库前查询缓存
        String json = jedisClient.get(itemInfo + ":" + itemId + ":DESC");
        try {
            if (StringUtils.isNotEmpty(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 缓存无查数据库
        TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            // 把查询结果添加到缓存
            jedisClient.set(itemInfo + ":" + itemId + ":DESC", JsonUtils.objectToJson(tbItemDesc));
            // 设置缓存过期时间，提高缓存的利用率
            jedisClient.expire(itemInfo + ":" + itemId+":DESC",seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }

    private void sendMsg(long itemId) {
        // 向activemq发送消息
        jmsTemplate.send(destination, session -> session.createTextMessage(itemId + ""));
    }
}
