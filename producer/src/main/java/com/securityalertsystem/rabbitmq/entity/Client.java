package com.securityalertsystem.rabbitmq.entity;

public class Client {
    private int[] location;
    private int[] address;
    private String clientId;

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public int[] getAddress() {
        return address;
    }

    public void setAddress(int[] address) {
        this.address = address;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


}
