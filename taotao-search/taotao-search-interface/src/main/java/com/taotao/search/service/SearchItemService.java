package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * 〈商品搜索接口〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/20
 */
public interface SearchItemService {
    /**
     * importItemsToIndex 商品数据导入索引库
     * @return Result 结果
     * @author MZRong
     * @date 2018/8/20 10:16
     */
    TaotaoResult importItemsToIndex();
}
