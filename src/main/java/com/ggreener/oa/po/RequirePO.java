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
public class RequirePO implements Serializable {
    private static final long serialVersionUID = -9074421629684450340L;
    private Long id;
    private Long companyId;
    private Long requirementId;
    private Date createTime;
    private String createUser;
}
