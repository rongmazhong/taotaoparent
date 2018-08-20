package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 〈查询结果pojo〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/8/20
 */
public class SearchResult implements Serializable {

    private long totalPages;
    private long recordCount;
    private List<SearchItem> list;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getList() {
        return list;
    }

    public void setList(List<SearchItem> list) {
        this.list = list;
    }
}
