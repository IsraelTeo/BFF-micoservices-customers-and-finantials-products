package com.microservice.BFF.connector.config;


public class EndpointConfiguration {
    private String url;
    private int readTimeOut;
    private int writeTimeOut;
    private int connectionTimeOut;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }
}