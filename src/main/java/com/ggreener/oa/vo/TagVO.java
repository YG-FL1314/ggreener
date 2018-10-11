package com.ggreener.oa.vo;

import java.io.Serializable;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
public class TagVO implements Serializable {
    private static final long serialVersionUID = 7788269578660760300L;

    private Long id;
    private String name;
    private Long parentId;
    private Integer order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
