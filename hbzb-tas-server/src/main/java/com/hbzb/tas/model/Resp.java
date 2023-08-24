package com.hbzb.tas.model;

public class Resp {
    private String code;
    private String message;
    private Object data;

    public Resp() {}

    public Resp(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Resp(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
