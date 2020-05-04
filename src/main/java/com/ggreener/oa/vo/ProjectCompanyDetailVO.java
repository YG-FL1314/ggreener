package com.ggreener.oa.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Data
public class ProjectCompanyDetailVO implements Serializable {
    private static final long serialVersionUID = -390731871687851042L;
    private Long id;
    private String people;
    private float amount;
    private String owners;
    private String others;
    private String projectName;
    private String companyName;
    private String projectType;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private String remark;
}
