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
public class DongdaemunAPIDTO {
    @JsonProperty("연번")
    private String 연번; // 연번 필드

    @JsonProperty("거리가게명")
    private String 거리가게명; // 거리 가게명 필드

    @JsonProperty("업종(요식업)")
    private String 업종; // 업종 필드 (요식업)

    @JsonProperty("주소")
    private String 주소; // 주소 필드

    @JsonProperty("데이터수집일자")
    private String 데이터수집일자; // 데이터 수집 일자 필드

}
