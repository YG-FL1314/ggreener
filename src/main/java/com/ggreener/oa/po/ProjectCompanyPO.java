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
public class ProjectCompanyPO implements Serializable {
    private static final long serialVersionUID = -8332444887820693676L;
    private Long id;
    private Long projectId;
    private Long companyId;
    private String people;
    private Long amount;
    private String own;
    private Long other;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}