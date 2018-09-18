package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈展示登陆和注册页面的controller〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/13
 */
@Controller
public class PageController {

    /**
     * showRegister 注册页面
     *
     * @return string
     * @author MZRong
     * @date 2018/9/13 15:21
     */
    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }


    /**
     * showLogin 展示登陆页面
     *
     * @param url   回调url
     * @param model 模型
     * @return java.lang.String
     * @author MZRong
     * @date 2018/9/18 15:36
     */
    @RequestMapping("/page/login")
    public String showLogin(String url, Model model) {

        model.addAttribute("redirect", url);
        return "login";
    }
}
