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
public class CompanyPO implements Serializable {
    private static final long serialVersionUID = 1441455311765181191L;
    private Long id;
    private String name;
    private Date establishedTime;
    private float registeredCapital;
    private String sharesCode;
    private String patents;
    private String utilityPatents;
    private String softwares;
    private float officeArea;
    private Long staffNumber;
    private Long technicians;
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
}
