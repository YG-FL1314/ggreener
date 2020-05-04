package com.ggreener.oa.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Data
public class ProjectCompanyVO implements Serializable {
    private static final long serialVersionUID = 8980460701558139064L;
    private Long id;
    private Long projectId;
    private Long companyId;
    private String people;
    private Long amount;
    private String own;
    private Long other;
    private String remark;
}
