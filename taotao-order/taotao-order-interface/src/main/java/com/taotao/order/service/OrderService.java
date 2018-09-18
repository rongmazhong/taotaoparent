package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * 〈订单服务〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/18
 */
public interface OrderService {
    TaotaoResult createOrder(OrderInfo orderInfo);

}
