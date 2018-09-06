package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * 〈item接口〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/4
 */
public interface ItemService {
    /**
     * 通过商品id获取商品信息
     * @param itemId 商品id
     * @return TbItem
     * @author MZRong
     * @date 2018/7/9 15:53
     */
    TbItem getItemById(Long itemId);

    /**
     * 获取所有商品
     * @param start 开始页
     * @param size 每页数据大小
     * @return EasyUIDataGridResult
     * @author MZRong
     * @date 2018/7/9 15:54
     */
    EasyUIDataGridResult getItemList(int start, int size);

    /**
     * 添加商品
     * @param item 商品
     * @param desc 商品描述
     * @return result
     */
    TaotaoResult addItem(TbItem item, String desc);

    /**
     * getItemDescById 通过id获取详情
     * @param itemId 商品id
     * @return TbItemDesc
     * @author MZRong
     * @date 2018/9/6 20:38
     */
    TbItemDesc getItemDescById(long itemId);

}
