package com.cpms.virtualization.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Rakib on 5/23/2017.
 */
public class MTCommConnectionManager {

    public MTCommConnectionManager() {
    }

    public Response sendToMachine(String operationXML, String URL) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL);
        post.setEntity(new StringEntity(operationXML));

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        return Response.status(response.getStatusLine().getStatusCode()).entity("Machine Operated Successfully").build();
    }

    public HttpResponse getFromMachine(String URL) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(URL);
        HttpResponse httpResponse = client.execute(httpGet);
        System.out.println("Response Code : " + httpResponse.getStatusLine().getStatusCode());
        return httpResponse;
    }
}
