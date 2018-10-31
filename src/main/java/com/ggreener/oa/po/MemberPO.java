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
public class MemberPO implements Serializable {
    private static final long serialVersionUID = -3425442816132955274L;
    private Long id;
    private Long companyId;
    private Long tagId;
    private String memberCode;
    private Date joiningTime;
    private Date validityTime;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
