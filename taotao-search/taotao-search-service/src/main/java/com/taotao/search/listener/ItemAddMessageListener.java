package com.taotao.search.listener;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * 〈activemq监听topic消息〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/6
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public void onMessage(Message message) {
        try {
            // 从消息中取出商品id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            long itemId = Long.parseLong(text);
            // 根据商品id查询数据，取出详细数据
            // 等待事务提交
            Thread.sleep(1000);
            SearchItem item = searchItemMapper.getItemById(itemId);
            if (item != null) {
                // 通过solr写入索引库
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id",item.getId());
                document.addField("item_title",item.getTitle());
                document.addField("item_sell_point",item.getSellPoint());
                document.addField("item_price",item.getPrice());
                document.addField("item_image",item.getImage());
                document.addField("item_category_name",item.getCategoryName());
                document.addField("item_desc",item.getItemDesc());
                solrClient.add(document);
                //提交
                solrClient.commit();
            }
            System.out.println("======>>>>>>成功添加商品到索引库");
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
