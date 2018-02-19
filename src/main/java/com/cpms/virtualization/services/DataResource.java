package com.cpms.virtualization.services;

import com.cpms.virtualization.models.Record;
import com.google.gson.Gson;
import com.sun.jersey.spi.resource.Singleton;
import org.apache.http.HttpStatus;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Rakib on 2/16/2018.
 */
@Path("data")
@Singleton
public class DataResource {

    private static final int interval = 60000;
    private boolean csv_flush_on = true;
    private List<Record> records;
    private final Object lock = new Object();

    class DataTimerTask extends TimerTask {

        @Override
        public void run() {
            if (csv_flush_on) {
                try {
                    CSVManager csvManager = new CSVManager();
                    synchronized (lock) {
                        csvManager.appendRecords(records);
                        records.clear();
                    }
                } catch (IOException e) {
                    System.err.println("Data Saving Failed, trying next time");
                }
            }
        }
    }

    public DataResource() throws InterruptedException {
        synchronized (lock) {
            records = new ArrayList<>();
        }
        Timer timer = new Timer();
        DataTimerTask dataTimerTask = new DataTimerTask();
        timer.schedule(dataTimerTask, interval, interval);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecord() {
        return Response.ok("test").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response appendRecord(String jsonString) {
        Gson gson = new Gson();
        Record record = gson.fromJson(jsonString, Record.class);
//        FIXME: Home Testing hack - Timediff between UoA pcs and Home pcs
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        record.setRenderTime(sdf.format(new Date()));
        if (csv_flush_on) {
            synchronized (lock) {
                records.add(record);
            }
        } else {
            return Response.ok("Record not stored").build();
        }
        return Response.ok("Success").build();
    }

    @POST
    @Path("csv-switch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stopCSVFlush(String truthVal) {
        this.csv_flush_on = Boolean.valueOf(truthVal);
        return Response.ok("Stopped").build();
    }
}
