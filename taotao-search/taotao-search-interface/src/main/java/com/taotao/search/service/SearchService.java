package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

/**
 * 〈搜索服务接口〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/20
 */
public interface SearchService {
    /**
     * search 搜索
     * @param queryString 搜索内容
     * @param page 页数
     * @param rows 数量
     * @return SearchResult
     * @exception exception 异常
     * @author MZRong
     * @date 2018/8/20 17:26
     */
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
