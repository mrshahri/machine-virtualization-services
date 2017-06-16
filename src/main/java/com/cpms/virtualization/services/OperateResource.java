package com.cpms.virtualization.services;

import com.cpms.virtualization.models.*;
import com.google.gson.Gson;
import nu.xom.ParsingException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Rakib on 11/22/2015.
 */
@Path("operate")
public class OperateResource {

    @POST
    @Path("device")
    @Produces(MediaType.APPLICATION_JSON)
    public Response operateDevice(String jsonString) {

        Gson gson = new Gson();
        DeviceOperation deviceOperation = gson.fromJson(jsonString, DeviceOperation.class);

        Config config = new Config();
        MTCommConnectionManager connectionManager = new MTCommConnectionManager();
        MTCommXmlManager xmlGenerator = new MTCommXmlManager();

        try {
            String operationXML = xmlGenerator.generateDeviceOperationForJobs(deviceOperation);
            Response response = connectionManager.sendToMachine(operationXML,
                    config.getMachine(deviceOperation.getDeviceId()).getOperate());
        } catch (IOException | ParsingException e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }

    @POST
    @Path("component")
    @Produces(MediaType.APPLICATION_JSON)
    public Response operateComponent(String jsonString) {
        Gson gson = new Gson();
        ComponentOperation operation = gson.fromJson(jsonString, ComponentOperation.class);

        Config config = new Config();
        MTCommConnectionManager connectionManager = new MTCommConnectionManager();
        MTCommXmlManager xmlGenerator = new MTCommXmlManager();

        try {
            String operationXML = xmlGenerator.generateComponentOperationForActions(operation);
            Response response = connectionManager.sendToMachine(operationXML,
                    config.getMachine(operation.getDeviceId()).getOperate());
        } catch (IOException | ParsingException e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }
}
