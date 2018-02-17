package com.cpms.virtualization.models;

import java.util.UUID;

/**
 * Created by Rakib on 2/16/2018.
 */
public class Record {

    private String status;
    private String readingTime;
    private String renderTime;

    public Record() {
        this.status = "";
        this.readingTime = "";
        this.renderTime = "";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }

    public String getRenderTime() {
        return renderTime;
    }

    public void setRenderTime(String renderTime) {
        this.renderTime = renderTime;
    }
}
