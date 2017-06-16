package com.cpms.virtualization.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rakib on 5/18/2017.
 */
public class ComponentOperation {
    private String deviceId;
    private String uuid;
    private String parentComponentId;
    private String componentId;
    private String componentName;
    private String component;
    private String operationId;
    private String componentValue;
    private String category;
    private String type;
    private String name;
    private HashMap<String, String> parameters;

    public ComponentOperation() {
        this.parentComponentId = "";
        this.componentId = "";
        this.component = "";
        this.componentName = "";
        this.operationId = "";
        this.componentValue = "";
        parameters = new HashMap<>();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponentValue() {
        return componentValue;
    }

    public void setComponentValue(String componentValue) {
        this.componentValue = componentValue;
    }

    public String getParentComponentId() {
        return parentComponentId;
    }

    public void setParentComponentId(String parentComponentId) {
        this.parentComponentId = parentComponentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
