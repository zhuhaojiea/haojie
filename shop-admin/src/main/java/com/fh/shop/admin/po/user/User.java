package com.fh.shop.admin.po.user;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private Long id;

    private String userName;

    private String realName;

    private String password;

    private Long shengId;

    private Long shiId;

    private Long xianId;

    private String shengName;

    private String shiName;

    private String xianName;

    private Date loginTime;

    private Integer loginCount;

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getShengName() {
        return shengName;
    }

    public void setShengName(String shengName) {
        this.shengName = shengName;
    }

    public String getShiName() {
        return shiName;
    }

    public void setShiName(String shiName) {
        this.shiName = shiName;
    }

    public String getXianName() {
        return xianName;
    }

    public void setXianName(String xianName) {
        this.xianName = xianName;
    }

    public Long getShengId() {
        return shengId;
    }

    public void setShengId(Long shengId) {
        this.shengId = shengId;
    }

    public Long getShiId() {
        return shiId;
    }

    public void setShiId(Long shiId) {
        this.shiId = shiId;
    }

    public Long getXianId() {
        return xianId;
    }

    public void setXianId(Long xianId) {
        this.xianId = xianId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
