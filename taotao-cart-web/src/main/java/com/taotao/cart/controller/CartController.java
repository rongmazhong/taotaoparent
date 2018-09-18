package com.taotao.cart.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈购物车controller〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/17
 */
@Controller
public class CartController {

    @Value("${CART_KEY}")
    private String CART_KEY;
    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;

    @Autowired
    private ItemService itemService;

    /**
     * addItemCart 添加购物车
     *
     * @param itemId 商品id
     * @param num    商品数量
     * @return java.lang.String
     * @author MZRong
     * @date 2018/9/17 14:26
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response) {
        // 取购物车商品列表
        List<TbItem> list = getCartItemList(request);
        boolean flag = false;
        // 判断商品在购物车是否存在
        for (TbItem tbItem : list) {
            if (tbItem.getId() == itemId.longValue()) {
                // 存在，数量相加
                tbItem.setNum(tbItem.getNum() + num);
                flag = true;
                break;
            }
        }
        // 不存在，添加一个新的商品
        if (!flag) {
            // 调用商品服务取商品信息
            TbItem item = itemService.getItemById(itemId);
            // 设置购买数量
            item.setNum(num);
            // 取一张照片
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            // 把商品添加购物车
            list.add(item);
        }
        // 把购物车写入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(list), CART_EXPIER, true);
        // 返回添加成功页面
        return "cartSuccess";
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
     * showCartList 展示购物车列表页
     *
     * @param request 请求
     * @return java.lang.String
     * @author MZRong
     * @date 2018/9/17 14:28
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request) {
        // 从cookie中取购物车列表
        List<TbItem> itemList = getCartItemList(request);
        // 把购物车列表传递给jsp
        request.setAttribute("cartList", itemList);
        // 返回逻辑视图
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateItemNum(@PathVariable Long itemId, @PathVariable Integer num,
                                      HttpServletRequest request, HttpServletResponse response) {
        // cookie中取购物车列表
        List<TbItem> cartItemList = getCartItemList(request);
        // 查询到对应商品
        for (TbItem it : cartItemList) {
            if (it.getId() == itemId.longValue()) {
                // 更新商品数量
                it.setNum(num);
                break;
            }
        }
        // 把购物车列表写入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
        // 返回成功
        return TaotaoResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
        // cookie中取购物车列表
        List<TbItem> cartItemList = getCartItemList(request);
        // 找到对应的商品
        for (TbItem item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                // 删除商品
                cartItemList.remove(item);
                break;
            }
        }
        // 把购物车列表写入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
        // 重定向到购物车列表页面
        return "redirect:/cart/cart.html";
    }
}
