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
public class GangNamAPIDTO {

    @JsonProperty("관리부서 전화번호")
    private String 관리부서전화번호;

    @JsonProperty("관리부서명")
    private String 관리부서명;

    @JsonProperty("구분")
    private String 구분;

    @JsonProperty("데이터기준일자")
    private String 데이터기준일자;

    @JsonProperty("소재지도로명주소")
    private String 소재지도로명주소;

    @JsonProperty("소재지지번주소")
    private String 소재지지번주소;

    @JsonProperty("취급물품")
    private String 취급물품;
}
