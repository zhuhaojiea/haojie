package com.fh.shop.admin.vo.user;

import java.io.Serializable;

public class UserVo implements Serializable {

    private Long id;

    private String userName;

    private String areaName;

    private Long shengId;

    private Long shiId;

    private Long xianId;

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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
