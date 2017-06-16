package com.cpms.virtualization.services;

import com.cpms.virtualization.models.Config;
import com.cpms.virtualization.models.Operations;
import com.google.common.base.Strings;
import nu.xom.ParsingException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Rakib on 5/25/2017.
 */
@Path("probe")
public class ProbeResource {

    @GET
    @Produces("application/json")
    public Response getOperations(@QueryParam("deviceId") String deviceId) {

        if (Strings.isNullOrEmpty(deviceId)) {
            return Response.serverError().status(HttpStatus.SC_BAD_REQUEST)
                    .entity("Bad Request").build();
        }
        Config config = new Config();
        MTCommConnectionManager connectionManager = new MTCommConnectionManager();
        MTCommXmlManager xmlGenerator = new MTCommXmlManager();
        Operations operations = new Operations();
        try {
            operations = xmlGenerator.getOperationsFromProbe(deviceId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParsingException e) {
            e.printStackTrace();
        }
        return Response.ok().entity(operations).build();
    }
}
