package com.example.bitcamptiger.seoulApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GangseoguAPIResponseDTO {

    @JsonProperty("currentCount")
    private int currentCount;  // 현재 개수

    @JsonProperty("data")
    private List<GangseoguAPIDTO> data;  // 데이터 리스트

    @JsonProperty("matchCount")
    private int matchCount;  // 매칭 개수

    @JsonProperty("page")
    private int page;  // 페이지 번호

    @JsonProperty("perPage")
    private int perPage;  // 페이지당 개수

    @JsonProperty("totalCount")
    private int totalCount;  // 전체 개수
}
