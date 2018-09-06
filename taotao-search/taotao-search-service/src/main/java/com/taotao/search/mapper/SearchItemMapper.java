package com.taotao.search.mapper;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * @author rongmahzong@outlook.com
 * @package com.taotao.search.mapper
 * @date Create in 下午 10:32 8.16/016
 */
public interface SearchItemMapper {
	/**
	 * 获取所有
	 * @return
	 */
	List<SearchItem> getItemList();

	/**
	 * getItemById  通过商品id查询商品详情
	 * @param itemId 商品id
	 * @return SearchItem 商品详情
	 * @author MZRong
	 * @date 2018/9/6 15:51
	 */
	SearchItem getItemById(long itemId);

}
