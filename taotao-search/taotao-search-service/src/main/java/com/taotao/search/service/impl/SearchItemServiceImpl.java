package com.taotao.search.service.impl;

import com.taotao.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈商品数据导入索引库〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/17
 */
@Service
public class SearchItemServiceImpl implements {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrClient solrClient;
    // 先查询商品的数据，遍历商品数据添加到索引库。
    //创建文档对象，向文档添加域，把文档写入索引库
    //提交
    //返回添加成功
}
