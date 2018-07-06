package com.taotao.common.pojo;

import java.io.Serializable;

/**
 * 〈类目tree〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/6
 */
public class EasyUITreeNode implements Serializable {
    private Long id;
    private String text;
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
