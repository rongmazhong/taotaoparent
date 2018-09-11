package com.taotao.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈测试html基础请求〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/10
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 测试freemarker
     * @author MZRong
     * @date 2018/9/10 14:41
     */
    @RequestMapping("/genhtml")
    @ResponseBody
    public String getHtml() throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map data = new HashMap(2);
        data.put("hello", "spring freemarker test");
        Writer out = new FileWriter("D:/Users/freemarker.html");
        template.process(data,out);
        out.close();
        return "OK";
    }
}
