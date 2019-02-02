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
public class BusinessDataPO implements Serializable {
    private static final long serialVersionUID = 1471801811139758848L;
    private Long id;
    private Long companyId;
    private String year;
    private float totalAsset;
    private float netAsset;
    private float revenue;
    private float profit;
    private String debtRatio;
    private float contractAmount;
    private float investedAmount;
    private Long number;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
