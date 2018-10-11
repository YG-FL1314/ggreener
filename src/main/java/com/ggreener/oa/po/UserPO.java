package com.ggreener.oa.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/9/30.
 *
 *
 */
@Data
public class UserPO implements Serializable {

    private static final long serialVersionUID = 9010881552539203951L;

    private String uuid;

    private String name;

    private String password;

    private Integer role;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
