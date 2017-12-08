package com.example.Demo.controller;

import com.google.gson.annotations.Expose;

/**
 * Created by hz on 12/7/17.
 */
public class RestJson {

    @Expose
    private String code;

    @Expose
    private String msg;
    @Expose
    private Object data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
