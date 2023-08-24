package com.example.bitcamptiger.gyeongGiApi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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