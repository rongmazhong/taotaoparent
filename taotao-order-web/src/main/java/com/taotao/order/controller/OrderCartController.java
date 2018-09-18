package com.taotao.order.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈订单控制器〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/18
 */
@Controller
public class OrderCartController {


    @Autowired
    private OrderService orderService;
    @Value("${CART_KEY}")
    private String CART_KEY;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        // 用户必须登陆（cookie中取token，sso）
        // 取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        // 根据用户信息取收获地址列表
        user.getId();
        // 把收获地址列表传递给页面
        // 从cookie中取商品列表展示到页面
        List<TbItem> cartItemList = getCartItemList(request);
        // 返回逻辑视图
        return "order-cart";
    }
    /**
     * getCartItemList 获取购物车列表
     *
     * @return java.util.List<com.taotao.pojo.TbItem>
     * @author MZRong
     * @date 2018/9/17 14:27
     */
    private List<TbItem> getCartItemList(HttpServletRequest request) {
        // 从cookie中取商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            // 如果没有内容，返回一个空的列表
            return new ArrayList<>();
        }
        List<TbItem> tbItems = JsonUtils.jsonToList(json, TbItem.class);
        return tbItems;
    }

    /**
     * 生成订单处理
     */
    @RequestMapping(value="/order/create", method= RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model) {
        //生成订单
        TaotaoResult result = orderService.createOrder(orderInfo);
        //返回逻辑视图
        model.addAttribute("orderId", result.getData().toString());
        model.addAttribute("payment", orderInfo.getPayment());
        //预计送达时间，预计三天后送达
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));

        return "success";
    }
}
