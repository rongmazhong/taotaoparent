package com.taotao.order.interceptor;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈登陆拦截器〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/18
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 执行handler前先执行此方法
        /**
         * 判断用户是否登陆
         * 1、从cookie中取token，没有跳转到登陆页，需要把当前请求的url作为参数传递给sso，登陆成功跳转回来。
         * 2、取token，调用sso服务判断是否登陆，如果没取到，跳转sso页面；
         * 3、取到放行
         */
        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            // 取当前请求的url
            String url = request.getRequestURL().toString();
            // 跳转到登陆页面
            response.sendRedirect(SSO_URL + "/page/login?url=" + url);
            // 拦截
            return false;
        }
        TaotaoResult user = userService.getUserByToken(token);
        if (user.getStatus() != 200) {
            // 取当前请求的url
            String url = request.getRequestURL().toString();
            // 跳转到登陆页面
            response.sendRedirect(SSO_URL + "/page/login?url=" + url);
            // 拦截
            return false;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // handler执行之后，modelAndView返回之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // modelAndView返回之后
    }
}
