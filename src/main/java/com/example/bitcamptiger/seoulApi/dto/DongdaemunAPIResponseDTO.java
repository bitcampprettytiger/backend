package com.example.bitcamptiger.seoulApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DongdaemunAPIResponseDTO {

    @JsonProperty("page")
    private int page; // 페이지 번호

    @JsonProperty("perPage")
    private int perPage; // 페이지 당 항목 수

    @JsonProperty("totalCount")
    private int totalCount; // 전체 항목 수

    @JsonProperty("currentCount")
    private int currentCount; // 현재 항목 수

    @JsonProperty("matchCount")
    private int matchCount; // 매칭된 항목 수

    @JsonProperty("data")
    private List<DongdaemunAPIDTO> data; // 실제 데이터 목록


}


