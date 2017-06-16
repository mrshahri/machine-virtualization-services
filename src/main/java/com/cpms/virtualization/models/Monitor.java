package com.cpms.virtualization.models;

import nu.xom.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rakib on 11/18/2015.
 */
public class Monitor {
    private String deviceName = "NA";
    private String deviceUUID = "NA";

    private String timeStamp = "NA";
    private String bedTemperature = "NA";
    private String nozzleTemperature = "NA";
    private String progress = "NA";
    private String availability = "NA";
    private String grabber = "NA";
    private String grabRotation = "NA";

    private String xPosition = "NA";
    private String yPosition = "NA";
    private String zPosition = "NA";

    public Monitor() {
    }

    public Monitor(InputStream is) throws ParsingException, IOException {
        Builder parser = new Builder();
        Document document = parser.build(is);
        XPathContext context = new XPathContext();
        context.addNamespace("xmlns", "urn:mtconnect.org:MTConnectStreams:1.2");

        // time stamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.timeStamp = sdf.format(new Date());

        // device name
        Nodes deviceNameNodes = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/@name",
                context);
        if (deviceNameNodes.size() > 0) {
            this.deviceName = deviceNameNodes.get(0).getValue();
        }

        // device uuid
        Nodes deviceUUIDNodes = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/@uuid",
                context);
        if (deviceUUIDNodes.size() > 0) {
            this.deviceUUID = deviceUUIDNodes.get(0).getValue();
        }

        // bed temperature
        Nodes bedTemperatureNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Samples/xmlns:Temperature[@dataItemId='bedTemp']",
                context);
        if (bedTemperatureNodes.size() > 0) {
            this.bedTemperature = bedTemperatureNodes.get(0).getValue();
        }

        // nozzle temperature
        Nodes nozzleTemperatureNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Samples/xmlns:Temperature[@dataItemId='extruderTemp']",
                context);
        if (nozzleTemperatureNodes.size() > 0) {
            this.nozzleTemperature = nozzleTemperatureNodes.get(0).getValue();
        }

        // progress
        Nodes progressNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Events/xmlns:Execution[@dataItemId='buildProgress']",
                context);
        if (progressNodes.size() > 0) {
            this.progress = progressNodes.get(0).getValue();
        }

        // availability
        Nodes availabilityNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Events/xmlns:Availability[@dataItemId='availability']",
                context);
        if (availabilityNodes.size() > 0) {
            this.availability = availabilityNodes.get(0).getValue();
        }

        //grabber
        Nodes grabberNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Samples/xmlns:Status[@dataItemId='Grabber']",
                context);
        if (grabberNodes.size() > 0) {
            this.grabber = grabberNodes.get(0).getValue();
        }

        //grab rotation
        Nodes grabRorationNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Samples/xmlns:Rotation[@dataItemId='Grab_rotation']",
                context);
        if (grabRorationNodes.size() > 0) {
            this.grabRotation = grabRorationNodes.get(0).getValue();
        }

        // x position
        Nodes xPositionNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Samples/xmlns:Position[@dataItemId='xPos']",
                context);
        if (xPositionNodes.size() > 0) {
            this.xPosition = xPositionNodes.get(0).getValue();
        }

        // y position
        Nodes yPositionNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Samples/xmlns:Position[@dataItemId='yPos']",
                context);
        if (yPositionNodes.size() > 0) {
            this.yPosition = yPositionNodes.get(0).getValue();
        }

        //z position
        Nodes zPositionNodes  = document.getRootElement().query(
                "/xmlns:MTConnectStreams/xmlns:Streams/xmlns:DeviceStream/xmlns:ComponentStream/xmlns:Samples/xmlns:Position[@dataItemId='zPos']",
                context);
        if (zPositionNodes.size() > 0) {
            this.zPosition = zPositionNodes.get(0).getValue();
        }
    }

    public Monitor(String timeStamp, String bedTemperature, String nozzleTemperature, String progress, String availability,
                   String grabber, String grabRotation) {
        this.timeStamp = timeStamp;
        this.bedTemperature = bedTemperature;
        this.nozzleTemperature = nozzleTemperature;
        this.progress = progress;
        this.availability = availability;
        this.grabber = grabber;
        this.grabRotation = grabRotation;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBedTemperature() {
        return bedTemperature;
    }

    public void setBedTemperature(String bedTemperature) {
        this.bedTemperature = bedTemperature;
    }

    public String getNozzleTemperature() {
        return nozzleTemperature;
    }

    public void setNozzleTemperature(String nozzleTemperature) {
        this.nozzleTemperature = nozzleTemperature;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }


    public String getGrabber() {
        return grabber;
    }

    public void setGrabber(String grabber) {
        this.grabber = grabber;
    }

    public String getGrabRotation() {
        return grabRotation;
    }

    public void setGrabRotation(String grabRotation) {
        this.grabRotation = grabRotation;
    }

    public String getxPosition() {
        return xPosition;
    }

    public void setxPosition(String xPosition) {
        this.xPosition = xPosition;
    }

    public String getyPosition() {
        return yPosition;
    }

    public void setyPosition(String yPosition) {
        this.yPosition = yPosition;
    }

    public String getzPosition() {
        return zPosition;
    }

    public void setzPosition(String zPosition) {
        this.zPosition = zPosition;
    }
}
