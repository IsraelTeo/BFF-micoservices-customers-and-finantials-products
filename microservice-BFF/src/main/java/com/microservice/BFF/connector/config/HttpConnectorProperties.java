package com.microservice.BFF.connector.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "http-connector")
public class HttpConnectorProperties {
    private Map<String, HostConfiguration> hosts;

    public Map<String, HostConfiguration> getHosts() {
        return hosts;
    }

    public void setHosts(Map<String, HostConfiguration> hosts) {
        this.hosts = hosts;
    }
}