import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

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
}
