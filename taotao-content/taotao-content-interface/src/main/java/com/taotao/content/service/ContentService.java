package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * 〈商品内容服务〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/17
 */
public interface ContentService {
    /**
     * addContent 添加内容
     * @param content 内容
     * @return TaotaoResult
     * @author MZRong
     * @date 2018/8/8 20:04
     */
    TaotaoResult addContent(TbContent content);

    List<TbContent> getContentByCid(Long cid);
}
