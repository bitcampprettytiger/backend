package com.example.bitcamptiger.RoadOccupancyCertificate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoadOccuCertiResponseDto {

    @JsonProperty("request_cnt")
    private int request_cnt;

    @JsonProperty("match_cnt")
    private int match_cnt;

    @JsonProperty("status_code")
    private String status_code;

    @JsonProperty("data")
    private RoadOcuuCertiData[] data;
}
