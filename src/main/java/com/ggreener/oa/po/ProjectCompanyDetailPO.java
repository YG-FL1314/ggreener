package com.ggreener.oa.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Data
public class ProjectCompanyDetailPO implements Serializable {
    private static final long serialVersionUID = -1527658646327708222L;
    private Long id;
    private Long projectId;
    private Long companyId;
    private String people;
    private BigDecimal amount;
    private String owners;
    private String others;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private String projectName;
    private String companyName;
    private Long projectType;
    private Date startDate;
    private Date endDate;
    private String remark;
}
