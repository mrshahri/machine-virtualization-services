package com.cpms.virtualization.services;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rakib on 11/21/2015.
 */
public class ConnectionManager {

    private static final int MIN_SIZE = 1;

    private Set<String> commands = new HashSet<>(Arrays.asList("stop", "reset", "remove", "pause", "resume"));

    public String executeSingle(InputStream inputStream, String controllerIp, String port) throws IOException {
        return printFile(inputStream, controllerIp, port, "load");
    }

    public String executeCollaboration(InputStream inputStream, String controllerIp, String port) throws IOException {
        return printFile(inputStream, controllerIp, port, "loadcoll");
    }

    public String printFile(InputStream inputStream, String controllerIp, String port, String loadCmd) throws IOException {

        String resultMessage = "Success";
        // port number of raspberry pi
        int portNumber = Integer.parseInt(port);

        byte[] fileBytes = getBytes(inputStream);
        Socket clientSocket = new Socket(controllerIp, portNumber);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        char[] buf;
        // get progress
        buf = new char[MIN_SIZE];
        outToServer.write(loadCmd.getBytes());
        inFromServer.read(buf);
        if (new String(buf).equals("1")) {
            outToServer.write(fileBytes);
            String output = new String(buf);
            StringTokenizer st = new StringTokenizer(output, ",");
        } else {
            resultMessage = "Failure";
        }
        clientSocket.close();
        return resultMessage;
    }

    public void commandMachine(String controllerIp, String port, String command) throws IOException {
        if (!commands.contains(command)) {
            throw new UnsupportedOperationException("Requested machine operation is not supported.");
        }
        Socket clientSocket = new Socket(controllerIp, Integer.parseInt(port));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.write(command.getBytes());
        if (clientSocket != null) {
            clientSocket.close();
        }
    }

    private byte[] getBytes(InputStream is) throws IOException {

        int len;
        int size = 5079000;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1)
                bos.write(buf, 0, len);
            buf = bos.toByteArray();
        }
        return buf;
    }
}