package com.cpms.virtualization.models;

import java.util.List;

/**
 * Created by Rakib on 5/25/2017.
 */
public class Operations {
    List<DeviceOperation> deviceOperations;
    List<ComponentOperation> componentOperations;

    public Operations() {
    }

    public List<DeviceOperation> getDeviceOperations() {
        return deviceOperations;
    }

    public void setDeviceOperations(List<DeviceOperation> deviceOperations) {
        this.deviceOperations = deviceOperations;
    }

    public List<ComponentOperation> getComponentOperations() {
        return componentOperations;
    }

    public void setComponentOperations(List<ComponentOperation> componentOperations) {
        this.componentOperations = componentOperations;
    }
}
