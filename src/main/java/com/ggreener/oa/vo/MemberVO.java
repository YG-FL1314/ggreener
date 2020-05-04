package com.ggreener.oa.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
@Getter
@Setter
public class MemberVO implements Serializable {
    private static final long serialVersionUID = 2003885731988953062L;
    private Long id;
    private Long companyId;
    private Long tagId;
    private String memberCode;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joiningTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validityTime;
    private Integer status;
}
