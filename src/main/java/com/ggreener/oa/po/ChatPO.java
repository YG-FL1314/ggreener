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
public class ChatPO implements Serializable {
    private static final long serialVersionUID = 8042938039463953271L;
    private Long id;
    private Long companyId;
    private Date chatTime;
    private Long chatType;
    private String chatAddress;
    private String customers;
    private String owners;
    private String content;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
}
