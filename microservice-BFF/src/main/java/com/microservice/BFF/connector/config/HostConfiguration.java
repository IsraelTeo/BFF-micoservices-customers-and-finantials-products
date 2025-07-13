package com.microservice.BFF.connector.config;


import java.util.Map;

public class HostConfiguration {
    private String host;
    private int port;
    private Map<String, EndpointConfiguration> endpoints;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<String, EndpointConfiguration> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Map<String, EndpointConfiguration> endpoints) {
        this.endpoints = endpoints;
    }
}