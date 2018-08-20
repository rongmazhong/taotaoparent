package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 〈商品数据导入索引库〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/17
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public TaotaoResult importItemsToIndex() {
        try {
// 先查询商品的数据，遍历商品数据添加到索引库。
            List<SearchItem> itemList = searchItemMapper.getItemList();
            //创建文档对象，向文档添加域，把文档写入索引库
            for (SearchItem searchItem : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id",searchItem.getId());
                document.addField("item_title",searchItem.getTitle());
                document.addField("item_sell_point",searchItem.getSellPoint());
                document.addField("item_price",searchItem.getPrice());
                document.addField("item_image",searchItem.getImage());
                document.addField("item_category_name",searchItem.getCategoryName());
                document.addField("item_desc",searchItem.getItemDesc());
                solrClient.add(document);
            }
            //提交
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "数据导入失败");
        }
        //返回添加成功
        return TaotaoResult.ok();
    }
}
