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
public class DobongguAPIDTO {
    @JsonProperty("도로주소")
    private String 도로주소;

    @JsonProperty("연번")
    private String 연번;

    @JsonProperty("운영품목")
    private String 운영품목;

    @JsonProperty("점용목적")
    private String 점용목적;

    @JsonProperty("점용장소")
    private String 점용장소;

    @JsonProperty("지정번호")
    private String 지정번호;
}
