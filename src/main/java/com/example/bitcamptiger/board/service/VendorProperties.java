package com.example.bitcamptiger.board.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vendor")
public class VendorProperties {
    private String validUrl;
    private String serviceKey;


    public String getValidUrl() {
        return validUrl;
    }

    public void setValidUrl(String validUrl) {
        this.validUrl = validUrl;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }


}