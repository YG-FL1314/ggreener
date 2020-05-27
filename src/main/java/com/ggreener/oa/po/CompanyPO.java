package com.ggreener.oa.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Data
public class CompanyPO implements Serializable {
    private static final long serialVersionUID = 1441455311765181191L;
    private Long id;
    private String name;
    private Date establishedTime;
    private Float registeredCapital;
    private String sharesCode;
    private String utilityPatents;
    private Long utilityPatentsTag;
    private String softwares;
    private Long staffNumber;
    private String products;
    private String telephone;
    private String fax;
    private String website;
    private String address;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private String honor;
    private String brief;
    private Long totalProjects;
    private Float income;
    private Float profit;
    private Float totalAssets;
    private List<Long> tags;
}
