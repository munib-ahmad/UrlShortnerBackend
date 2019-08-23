package com.urlshortner.corsFilter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
public class WsCorsFilter implements ContainerResponseFilter {

	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", this.getAllowedHeaders());
		responseContext.getHeaders().add("Access-Control-Expose-Headers", this.getAllowedHeaders());
	}

	private String getAllowedHeaders() {
		StringBuilder sb = new StringBuilder();
		sb.append(HttpHeaders.ACCEPT).append(", ").append(HttpHeaders.CONTENT_TYPE).append(", ");

		return sb.toString();
	}
}
