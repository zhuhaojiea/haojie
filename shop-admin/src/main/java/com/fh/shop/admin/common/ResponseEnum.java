package com.fh.shop.admin.common;

public enum ResponseEnum {

    SPEC_INFO_IS_NULL(2000, "规格信息为空"),

    SKU_INFO_IS_EMPTY(3000, "SKU的相关信息为空"),

    USERNAME_IS_NOT_EXIST(1001, "用户名不存在"),
    USER_PASSWORD_IS_ERROR(1002, "密码错误"),
    USERNAME_PASSWORD_IS_NULL(1000, "用户名或者密码为空");

    private int code;

    private String msg;

    private ResponseEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
