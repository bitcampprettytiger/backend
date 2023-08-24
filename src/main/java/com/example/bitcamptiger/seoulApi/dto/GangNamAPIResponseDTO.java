package com.example.bitcamptiger.seoulApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GangNamAPIResponseDTO {

    @JsonProperty
    private int currentCount;

    @JsonProperty
    private List<GangNamAPIDTO> data;
    @JsonProperty
    private int matchCount;
    @JsonProperty
    private int page;  // 추가

    @JsonProperty
    private int perPage;

    @JsonProperty
    private int totalCount;
}
