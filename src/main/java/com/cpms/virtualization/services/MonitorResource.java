package com.cpms.virtualization.services;

import com.cpms.virtualization.models.Config;
import com.cpms.virtualization.models.Monitor;
import com.sun.jersey.spi.container.ContainerResponse;
import nu.xom.ParsingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakib on 6/14/2016.
 */
@Path("monitor")
public class MonitorResource {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Monitor controlMachine(@QueryParam("deviceId") String deviceId)
            throws IOException, ParsingException {
//        requestResponse.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        Config config = new Config();
//        List<Monitor> monitors = new ArrayList<>();
//        for (String machineMonitorUrl : config.getMachine(deviceId)) {
        MTCommConnectionManager connectionManager = new MTCommConnectionManager();
        String machineMonitorUrl = config.getMachine(deviceId).getMonitor();
        HttpResponse response = connectionManager.getFromMachine(machineMonitorUrl);
        Monitor monitor = new Monitor(response.getEntity().getContent());
//        monitors.add(monitor);
//        }
        return monitor;
    }
}