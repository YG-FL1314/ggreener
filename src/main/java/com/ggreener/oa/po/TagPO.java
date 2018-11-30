package com.ggreener.oa.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Data
public class TagPO implements Serializable {
    private static final long serialVersionUID = 1589161674271379294L;
    private Long id;
    private String name;
    private Long parentId;
    private Integer order;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
