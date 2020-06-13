package com.fh.shop.admin.vo.log;

import java.io.Serializable;
import java.util.Date;

public class LogVo implements Serializable {

    private Long id;

    private String userName;

    private String realName;

    private String info;

    private String insertTime;

    private Integer status;

    private String content;

    private String paramInfo;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParamInfo() {
        return paramInfo;
    }

    public void setParamInfo(String paramInfo) {
        this.paramInfo = paramInfo;
    }
}
