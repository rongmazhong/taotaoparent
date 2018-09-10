package com.taotao.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈测试Freemarker〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/7
 */
public class TestFreeMarker {

    @Test
    public void testFreemarker()throws Exception {
        // 1、创建模板
        // 2、创建一个configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 3、设置模板所在路径
        configuration.setDirectoryForTemplateLoading(new File("D:/taotaoparent/taotao-item-web/src/main/webapp/WEB-INF/ftl"));
        // 4、设置模板的字符集，一般为utf-8
        configuration.setDefaultEncoding("utf-8");
        // 5、使用configuration对象加载一个模板对象，需要指定模板文件的文件名
        Template template = configuration.getTemplate("student.ftl");
        // 6、创建一个数据集，可以是pojo也可以是map，推荐map
        Map data = new HashMap();
        data.put("hello", "hello freemarker");
        Student student = new Student(1,"小米",11,"重庆市渝北区");
        data.put("student", student);
        // 7、创建一个Write对象
        Writer out = new FileWriter(new File("D:/Users/studen.html"));
        // 8、使用模板对象的process方法输出文件
        template.process(data,out);
        // 9、关闭流
        out.close();
    }
}
