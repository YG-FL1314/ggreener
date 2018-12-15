package com.ggreener.oa.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM", timezone = "GMT+8")
    private Date chatTime;
    private String chatType;
    private String chatAddress;
    private String customers;
    private String owners;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getChatTime() {
        return chatTime;
    }

    public void setChatTime(Date chatTime) {
        this.chatTime = chatTime;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getChatAddress() {
        return chatAddress;
    }

    public void setChatAddress(String chatAddress) {
        this.chatAddress = chatAddress;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
