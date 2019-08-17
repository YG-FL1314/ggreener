package com.ggreener.oa.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/12/3.
 * <p>
 * XXX
 */
@Data
public class CompanyOverviewPO implements Serializable {
    private static final long serialVersionUID = -2573759551381331607L;
    private Long id;
    private String name;
    private Date establishedTime;
    private float registeredCapital;
    private String memberName;
    private String memberCode;
    private Date updateTime;
    private String updateUser;
}
