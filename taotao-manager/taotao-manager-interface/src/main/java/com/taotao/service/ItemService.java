package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;

/**
 * 〈item接口〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/4
 */
public interface ItemService {

    TbItem getItemById(Long itemId);

    EasyUIDataGridResult getItemList(int start, int size);

}
