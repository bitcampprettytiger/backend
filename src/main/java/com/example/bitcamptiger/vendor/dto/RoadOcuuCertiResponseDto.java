
package com.example.bitcamptiger.vendor.dto;

        import com.fasterxml.jackson.annotation.JsonProperty;
        import lombok.Data;

@Data
public class RoadOcuuCertiResponseDto {

    @JsonProperty("request_cnt")
    private int requestCnt;

    @JsonProperty("match_cnt")
    private int matchCnt;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("data")
    private RoadOcuuCertiData[] data;
}
