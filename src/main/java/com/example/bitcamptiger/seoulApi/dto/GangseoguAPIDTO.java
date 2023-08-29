package com.example.bitcamptiger.seoulApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GangseoguAPIDTO {

    @JsonProperty("거리가게 유형")
    private String 거리가게_유형;  // 거리가게 유형

    @JsonProperty("데이터 기준일자")
    private String 데이터_기준일자;  // 데이터 기준일자

    @JsonProperty("비고")
    private String 비고;  // 비고

    @JsonProperty("시군구명")
    private String 시군구명;  // 시군구명

    @JsonProperty("위치")
    private String 위치;  // 위치

    @JsonProperty("판매품목")
    private String 판매품목;  // 판매품목

    @JsonProperty("허가기간")
    private String 허가기간;  // 허가기간

}
