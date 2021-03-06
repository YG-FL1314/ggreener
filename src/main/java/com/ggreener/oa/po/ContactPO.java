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
public class ContactPO implements Serializable {
    private static final long serialVersionUID = 1441455311765181191L;
    private Long id;
    private Long companyId;
    private String name;
    private String telephone;
    private String phone;
    private String duty;
    private String mail;
    private String weixin;
    private String qq;
    private String remark;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private Integer order;
}
