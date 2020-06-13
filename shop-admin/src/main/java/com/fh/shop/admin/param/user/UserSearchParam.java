package com.fh.shop.admin.param.user;

import com.fh.shop.admin.param.Page;

public class UserSearchParam extends Page {

    private Long shengId;

    private Long shiId;

    private Long xianId;

    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
