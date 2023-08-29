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
public class DongJakAPIDTO {
    @JsonProperty("연번")
    private int 연번;
    @JsonProperty("구분")
    private String 구분;
    @JsonProperty("거리가게명")
    private String 거리가게명;
    @JsonProperty("업종(판매품목)")
    private String 업종_판매품목;
    @JsonProperty("위치")
    private String 위치;
    @JsonProperty("데이터기준일자")
    private String 데이터기준일자;
}
