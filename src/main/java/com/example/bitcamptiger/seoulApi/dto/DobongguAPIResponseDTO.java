package com.example.bitcamptiger.seoulApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DobongguAPIResponseDTO {
    @JsonProperty("currentCount")
    private int currentCount;  // 현재 데이터 개수

    @JsonProperty("data")
    private List<DobongguAPIDTO> data;  // 데이터 목록

    @JsonProperty("matchCount")
    private int matchCount;  // 매치된 데이터 개수

    @JsonProperty("page")
    private int page;  // 페이지 번호

    @JsonProperty("perPage")
    private int perPage;  // 페이지당 데이터 개수

    @JsonProperty("totalCount")
    private int totalCount;  // 총 데이터 개수

}
