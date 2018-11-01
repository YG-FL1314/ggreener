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
public class HolderPO implements Serializable {
    private static final long serialVersionUID = -8139547319269501562L;
    private Long id;
    private Long companyId;
    private String name;
    private Double amount;
    private String percent;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
