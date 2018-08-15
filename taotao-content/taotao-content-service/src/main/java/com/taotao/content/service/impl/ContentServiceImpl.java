package com.taotao.content.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 〈商品内容服务〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/17
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;

	@Override
	public TaotaoResult addContent(TbContent content) {
		//补全pojo属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//插入到内容表
		contentMapper.insert(content);
		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentByCid(Long cid) {
		//先查缓存
		// 添加缓存不能影响正常业务逻辑
		try {
			String hget = jedisClient.hget("INDEX_CONTENT", String.valueOf(cid));
			if (StringUtils.isNotEmpty(hget)) {
				List<TbContent> list = JsonUtils.jsonToList(hget, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//无缓存，查数据库
		TbContentExample contentExample = new TbContentExample();
		TbContentExample.Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> contents = contentMapper.selectByExample(contentExample);
		//结果添加到缓存
		try {
			jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(contents));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}
}
