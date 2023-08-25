package com.example.bitcamptiger.gyeongGiApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RowDTO {
    private String prmsnNm;
    private String refineLotnoAddr;
    private String refineZipno;
    private String induTypeNm;
    private String licensgDe;
    private String sigunNm;
    private double occupTnAr;
    private String refineRoadnmAddr;
    private String refineWgs84Lat;
    private String storeNm;
    private String clsbizYn;
    private String refineWgs84Logt;
}