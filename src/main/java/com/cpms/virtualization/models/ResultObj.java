package com.cpms.virtualization.models;

/**
 * Created by Rakib on 3/31/2016.
 */
public class ResultObj {
    private String result;
    private String message;

    public ResultObj() {
    }

    public ResultObj(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
