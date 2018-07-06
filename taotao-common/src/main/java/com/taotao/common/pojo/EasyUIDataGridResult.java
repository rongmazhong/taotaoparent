package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 〈EasyUI公共类 分页查询〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/6
 */
public class EasyUIDataGridResult implements Serializable {
    private Long total;
    private List rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
