package com.example.bitcamptiger.vendor.service;

import org.json.simple.JSONObject;

public interface GeoService {
    JSONObject geocoding(String address);

}
