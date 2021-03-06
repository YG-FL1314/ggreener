package com.ggreener.oa.vo;

import java.io.Serializable;

/**
 * Created by lifu on 2018/9/30.
 * <p>
 * XXX
 */
public class UserVO implements Serializable {

    private static final long serialVersionUID = -504988295594176813L;

    private String uuid;
    private String name;
    private Integer role;
    private Integer status;
    private String nickName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
