package com.taotao.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈全局异常处理器〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/20
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, Exception e) {
        LOGGER.info("进入全局异常处理器。。。。。");
        LOGGER.debug("测试handler的类型：" + o.getClass());
        //控制台打印异常
        e.printStackTrace();
        //向日志文件中写入异常
        LOGGER.error("系统发生异常", e);
        //发邮件
        //jmail
        //发短信
        //展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "您的电脑有问题，请稍后重试。");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
