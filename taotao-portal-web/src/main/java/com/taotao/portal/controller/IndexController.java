package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈首页〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/9
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String showIndex() {
        return "index";
    }
}
