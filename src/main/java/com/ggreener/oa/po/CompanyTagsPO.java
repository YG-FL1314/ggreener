package com.ggreener.oa.po;

import lombok.Data;

import java.util.Date;

/**
 * Created by lifu on 2018/10/22.
 * <p>
 * XXX
 */
@Data
public class CompanyTagsPO {
    private Long id;
    private Long companyId;
    private Long tagId;
    private Date time;
}
