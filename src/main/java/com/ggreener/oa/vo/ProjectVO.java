package com.ggreener.oa.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lifu on 2018/9/30.
 * <p>
 * XXX
 */
@Data
public class ProjectVO implements Serializable {

    private static final long serialVersionUID = 2355760152215919848L;

    private Long id;
    private String name;
    private String type;
    private Integer companyCount;
    private Integer people;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private String remark;
    private float amount;

}
