import org.apache.commons.collections4.CollectionUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 〈测试solrJ〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/17
 */
public class TestSolrJ {
    @Test
    public void testAddDocument() throws IOException, SolrServerException {
        //创建solrServer对象，创建httpSolrServer
        // 指定solr服务url
        String urlString = "http://10.0.0.70:8983/solr/core1";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();
        // 创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域，必须有id域，域的名称必须在schema.xml中定义
        document.addField("id","test001");
        document.addField("item_title","测试产品1");
        document.addField("item_price",100);
        //把文档对象写入索引库
        solr.add(document);
        //提交
        solr.commit();
    }

    /**
     * deleteById 通过id删除
     * @author MZRong
     * @date 2018/8/17 15:41
     */
    @Test
    public void deleteById() throws IOException, SolrServerException {
        String urlString = "http://10.0.0.70:8983/solr/core1";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();
        solr.deleteById("test001");
        solr.commit();
    }

    /**
     * deleteByQuery 查询删除
     * @author MZRong
     * @date 2018/8/17 15:43
     */
    @Test
    public void deleteByQuery() throws IOException, SolrServerException {
        String urlString = "http://10.0.0.70:8983/solr/core1";
        SolrClient solr = new HttpSolrClient.Builder(urlString).build();
        solr.deleteByQuery("id:test001");
        solr.commit();
    }
    @Test
    public void searchDocument() {
        try {
            //创建一个client对象
            String urlString = "http://10.0.0.70:8983/solr/core1";
            SolrClient solr = new HttpSolrClient.Builder(urlString).build();
            //创建一个solrQuery对象
            SolrQuery solrQuery = new SolrQuery();
            //设置查询条件
            // solrQuery.set("q", "*:*");
            solrQuery.setQuery("手机");
            //分页条件
            solrQuery.setStart(11);
            solrQuery.setRows(10);
            //设置默认搜索域
            solrQuery.set("df", "item_keywords");
            //设置高亮
            solrQuery.setHighlight(true);
            solrQuery.addHighlightField("item_title");
            solrQuery.setHighlightSimplePre("<em>");
            solrQuery.setHighlightSimplePost("</em>");
            //执行查询，返回response对象
            QueryResponse response = solr.query(solrQuery);
            //取查询结果
            SolrDocumentList results = response.getResults();
            //取总记录数
            System.out.println("查询总记录数：" + results.getNumFound());
            for (SolrDocument d : results ) {
                System.out.println(d.get("id"));
                //取高亮显示
                Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
                List<String> list = highlighting.get(d.get("id")).get("item_title");
                String itemTitle = "";
                if (CollectionUtils.isNotEmpty(list)) {
                    itemTitle = list.get(0);
                } else {
                    itemTitle = (String)d.get("item_title");
                }
                System.out.println(itemTitle);
                System.out.println(d.get("item_title"));
                System.out.println(d.get("item_sell_point"));
                System.out.println(d.get("item_price"));
                System.out.println(d.get("item_image"));
                System.out.println(d.get("item_category_name"));
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
