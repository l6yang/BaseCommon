package com.loyal.base.sample.beans;

import com.google.gson.annotations.SerializedName;
import com.loyal.base.beans.PatternBean;

public class ResultBean<T> extends PatternBean<T> {
    private String code;
    @SerializedName("msg")
    private String message;
    private T obj;

    public ResultBean() {

    }

    public ResultBean(String code, String message, T obj) {
        this.code = code;
        this.message = message;
        this.obj = obj;
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

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
