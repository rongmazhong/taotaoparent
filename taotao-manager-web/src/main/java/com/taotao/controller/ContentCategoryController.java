package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.R;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 〈内容分类管理〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/16
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> contentCategoryList = contentCategoryService.getContentCategoryList(parentId);
        return contentCategoryList;
    }

    @RequestMapping("/content/category/listtest")
    @ResponseBody
    public R getContentCategoryListtest(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> contentCategoryList = contentCategoryService.getContentCategoryList(parentId);
        return R.ok().put("data",contentCategoryList);
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult addContentCategory(Long parentId, String name) {
        TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
        return result;
    }
}
