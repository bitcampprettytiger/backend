package com.example.bitcamptiger;

import com.example.bitcamptiger.vendor.service.VendorProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
public class BitcamptigerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitcamptigerApplication.class, args);
    }

}
