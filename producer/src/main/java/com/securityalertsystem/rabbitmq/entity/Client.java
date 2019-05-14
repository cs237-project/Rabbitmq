package com.securityalertsystem.rabbitmq.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private int clientId;
    private double locationx;
    private double locationy;
    private double addressx;
    private double addressy;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getLocationx() {
        return locationx;
    }

    public void setLocationx(double locationx) {
        this.locationx = locationx;
    }

    public double getLocationy() {
        return locationy;
    }

    public void setLocationy(double locationy) {
        this.locationy = locationy;
    }

    public double getAddressx() {
        return addressx;
    }

    public void setAddressx(double addressx) {
        this.addressx = addressx;
    }

    public double getAddressy() {
        return addressy;
    }

    public void setAddressy(double addressy) {
        this.addressy = addressy;
    }
}
