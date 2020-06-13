package com.fh.shop.admin.common;

public class ServerResponse {

    private int code;

    private String msg;

    private Object data;


    private ServerResponse() {

    }


    private ServerResponse(int code, String msg, Object data) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }


    public static ServerResponse success() {
        return new ServerResponse(200, "ok", null);
    }

    public static ServerResponse success(Object data) {
        return new ServerResponse(200, "ok", data);
    }

    public static ServerResponse error() {
        return new ServerResponse(-1, "error", null);
    }

    public static ServerResponse error(int code, String msg) {
        return new ServerResponse(code, msg, null);
    }

    public static ServerResponse error(ResponseEnum responseEnum) {
        return new ServerResponse(responseEnum.getCode(), responseEnum.getMsg(), null);
    }

    public int getCode() {
        return code;
    }



    public String getMsg() {
        return msg;
    }



    public Object getData() {
        return data;
    }


}
