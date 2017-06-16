package com.cpms.virtualization.models;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Rakib on 2/22/2016.
 */
public class Config {

    private ManufacturingSite site;

    public Config() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("config.json").getFile());
            //Object to JSON in file
            site = new ManufacturingSite();
            JsonReader reader = new JsonReader(new FileReader(file));
            Gson gson = new Gson();
            this.site = gson.fromJson(reader, ManufacturingSite.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Machine getMachine(String deviceId) {
        for (Machine machine: site.getMachines()) {
            if (deviceId.equals(machine.getDeviceId()))
                return machine;
        }
        return null;
    }

/*
    public static void main(String[] args) {
        Config config = new Config();
        System.out.println(config.getMachine("ultimaker"));
    }
*/
}
