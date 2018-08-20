package com.taotao.search.dao;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈查询索引库商品dao〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/20
 */
@Repository
public class SearchDao {
    @Autowired
    private SolrClient solrClient;

    public SearchResult search(SolrQuery query) throws Exception{
        //根据query对象进行查询
        QueryResponse response = solrClient.query(query);
        //取查询结果
        SolrDocumentList results = response.getResults();
        //取查询结果总记录数
        long numFound = results.getNumFound();
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument document : results) {
            SearchItem searchItem = new SearchItem();
            searchItem.setCategoryName(String.valueOf(document.get("item_category_name")));
            searchItem.setId(String.valueOf(document.get("id")));
            searchItem.setImage((String) document.get("item_image"));
            searchItem.setPrice((Long) document.get("item_price"));
            searchItem.setSellPoint((String) document.get("item_sell_point"));
            // 取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            String title = "";
            if (CollectionUtils.isNotEmpty(list)) {
                title = list.get(0);
            } else {
                title = (String) document.get("item_title");
            }
            searchItem.setTitle(title);
            //把结果封装到searchItem对象中
            itemList.add(searchItem);
        }
        //把结果添加到SearchResult对象中
        searchResult.setList(itemList);
        //返回
        return searchResult;
    }
}
