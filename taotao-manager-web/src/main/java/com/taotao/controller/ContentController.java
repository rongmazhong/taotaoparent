package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 〈内容管理Controller〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/17
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    public TaotaoResult addContent(TbContent content) {
        TaotaoResult result = contentService.addContent(content);
        return TaotaoResult.ok(result);
    }
}
