package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈page 页面展示〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/6
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
