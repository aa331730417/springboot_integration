package com.luqi.demo.dto;

/**
 * @Author: I_UO_I
 * @Date: 2019/1/18 11:19
 * @Version 1.0
 */
public class JsonResult {

    public static final String SUCCESS = "SUCCESS";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";

    private String status = null;

    private Object result = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
