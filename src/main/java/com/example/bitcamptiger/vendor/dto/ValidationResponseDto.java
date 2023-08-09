package com.example.bitcamptiger.vendor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidationResponseDto {
    @JsonProperty("request_cnt")
    private int request_cnt;

    @JsonProperty("match_cnt")
    private int match_cnt;

    @JsonProperty("status_code")
    private String status_code;

    @JsonProperty("data")
    private VendorValidationDto[] data;
}