package com.example.bitcamptiger.vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadOcuuCertiData {

    private String rqsNo;
    private String prmCd;
    private String prmNm;
    private String orcd;
    private String ornm;
    private String lgnId;
    private String edDt;
    private String edTyCd;
    private String edRsn;
    private String feeCts;
    private String perNo;
    private String pmsnRectMtdCd;
    private String rlAppiNm;
    private String onlPmsnNo;
    private String lstUpdDe;
    private String masterLinkCnt;
}
