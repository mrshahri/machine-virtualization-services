package com.cpms.virtualization.models;

import java.util.HashMap;

/**
 * Created by Rakib on 5/25/2017.
 */
public class Parameter {
    private String id;
    private String name;
    private String type;
    private String value;
    private HashMap<String, String> restAttributes;

    public Parameter() {
        this.id = "";
        this.name = "";
        this.type = "";
        this.value = "";
        this.restAttributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HashMap<String, String> getRestAttributes() {
        return restAttributes;
    }

    public void setRestAttributes(HashMap<String, String> restAttributes) {
        this.restAttributes = restAttributes;
    }
}
