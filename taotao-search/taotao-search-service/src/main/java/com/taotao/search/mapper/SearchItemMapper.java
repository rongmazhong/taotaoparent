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
}
