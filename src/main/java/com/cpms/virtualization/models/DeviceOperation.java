package com.cpms.virtualization.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Rakib on 5/17/2017.
 */
public class DeviceOperation {
    private String deviceId;
    private String operationId;
    private List<Parameter> parameters;

    public DeviceOperation() {
        parameters = new ArrayList<>();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
