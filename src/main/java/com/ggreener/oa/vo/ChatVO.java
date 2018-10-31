package com.ggreener.oa.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lifu on 2018/10/8.
 * <p>
 * XXX
 */
public class ChatVO implements Serializable {
    private static final long serialVersionUID = -8437341101082086639L;
    private Long id;
    private Long companyId;
    private Date chatTime;
    private Long chatType;
    private String customers;
    private String owners;
    private String content;
}
