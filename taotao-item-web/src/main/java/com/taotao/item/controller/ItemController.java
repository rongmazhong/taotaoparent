package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈商品详情控制器〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/9/6
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable long itemId, Model model) {
        TbItem itemById = itemService.getItemById(itemId);
        Item item = new Item(itemById);
        TbItemDesc itemDescById = itemService.getItemDescById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDescById);
        return "item";
    }
}
