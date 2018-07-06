package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * 〈商品类目〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/6
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(Long parentId);

}
