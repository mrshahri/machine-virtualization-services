package com.cpms.virtualization.filters;

import com.google.common.base.Strings;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * Created by Rakib on 12/11/2015.
 */
public class CORSFilter implements ContainerResponseFilter {
        @Override
        public ContainerResponse filter(ContainerRequest request,
                                        ContainerResponse response) {

            if (Strings.isNullOrEmpty((String) response.getHttpHeaders().getFirst("Access-Control-Allow-Origin"))) {
                response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
            }
            if (Strings.isNullOrEmpty((String) response.getHttpHeaders().getFirst("Access-Control-Allow-Headers"))) {
                response.getHttpHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
            }
            if (Strings.isNullOrEmpty((String) response.getHttpHeaders().getFirst("Access-Control-Allow-Credentials"))) {
                response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
            }
            if (Strings.isNullOrEmpty((String) response.getHttpHeaders().getFirst("Access-Control-Allow-Methods"))) {
                response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            }

            return response;
        }
}