package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * 〈商品详情服务〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/16
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCategoryList(long parentID);

    TaotaoResult addContentCategory(Long parentId, String name);


}
