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

    /**
     * 添加
     * @param parentId 父节点id
     * @param name 节点名称
     * @return TaotaoResult
     */
    TaotaoResult addContentCategory(Long parentId, String name);

    /**
     * 修改节点
     * @param id 节点id
     * @param name 节点名称
     * @return TaotaoResult
     * @author MZRong
     * @date 2018/8/8 19:32
     */
    TaotaoResult updateContentCategory(Long id, String name);

}
