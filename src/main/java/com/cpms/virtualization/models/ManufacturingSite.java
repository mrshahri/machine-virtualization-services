package com.cpms.virtualization.models;

import java.util.List;

/**
 * Created by Rakib on 5/23/2017.
 */
public class ManufacturingSite {

    private List<Machine> machines;
    private String authorizationUrl;

    public ManufacturingSite() {
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }
}
