package com.cpms.virtualization.models;

/**
 * Created by Rakib on 11/29/2015.
 */
public class Characteristics {

    private String machineModelName;
    private String company;
    private String machineType;
    private String material;
    private String maxWidth;
    private String maxDepth;
    private String maxHeight;
    private String buildAreaShape;
    private String connectionType;

    public Characteristics() {
        machineModelName = "";
        company = "";
        machineType = "";
        material = "";
        maxWidth = "";
        maxDepth = "";
        maxHeight = "";
        buildAreaShape = "";
        connectionType = "";
    }

    public String getMachineModelName() {
        return machineModelName;
    }

    public void setMachineModelName(String machineModelName) {
        this.machineModelName = machineModelName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(String maxWidth) {
        this.maxWidth = maxWidth;
    }

    public String getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(String maxDepth) {
        this.maxDepth = maxDepth;
    }

    public String getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(String maxHeight) {
        this.maxHeight = maxHeight;
    }

    public String getBuildAreaShape() {
        return buildAreaShape;
    }

    public void setBuildAreaShape(String buildAreaShape) {
        this.buildAreaShape = buildAreaShape;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
}
