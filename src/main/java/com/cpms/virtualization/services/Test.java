package com.cpms.virtualization.services;

import nu.xom.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Rakib on 11/21/2015.
 */
public class Test {
/*
    public static void main(String[] args) {
        // host ip of raspberry pi
        String hostName = "130.184.104.182";
        // port number of raspberry pi
        int portNumber = Integer.parseInt("12345");

        char[] buf = new char[1024];

        try {
            Socket clientSocket = new Socket(hostName, portNumber);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // connect the printer
            outToServer.write("connect".getBytes());
            inFromServer.read(buf);
            System.out.println("From Server : " +  new String(buf));

            // get progress
            outToServer.write("monitor".getBytes());
            buf = new char[100];
            inFromServer.read(buf);
            System.out.println("From Server : " +  new String(buf));

            // disconnect machine
            outToServer.write("disconnect".getBytes());
            buf = new char[100];
            inFromServer.read(buf);
            System.out.println("From Server : " + new String(buf));

            clientSocket.close();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    public static void main(String[] args) {
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://130.184.104.182:1080/current");
            request.addHeader("accpet", "application/xml");
            HttpResponse response = httpClient.execute(request);

            Builder parser = new Builder();
            Document document = parser.build(response.getEntity().getContent());
            System.out.println(document.toXML());
            XPathContext context = new XPathContext();
            //context.addNamespace("xmlns", "urn:mtconnect.org:MTConnectDevices:1.2");
            context.addNamespace("xmlns", "urn:mtconnect.org:MTConnectStreams:1.2");
            Nodes headers = document.query("/xmlns:MTConnectStreams/xmlns:Header", context);
            System.out.println(headers.get(0).toXML());
        } catch (IOException | ParsingException e) {
            e.printStackTrace();
        }
    }
}
