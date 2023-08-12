package com.example.bitcamptiger.vendor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//    private String rqsNo; //신청번호
//    private String prmCd; //인허가코드
//    private String prmNm; //인허가명
//    private String orcd; //기관코드
//    private String ornm; //기관명
//    private String lgnId; //로그인ID
//    private String edDt; //종료일자
//    private String edTyCd; //종료유형코드
//    private String edRsn; //종료사유
//    private String feeCts; //수수료내용
//    private String perNo; //허가번호
//    private String pmsnRectMtdCd; //허가서수령방법코드
//    private String rlAppiNm; //실제신청자명
//    private String onlPmsnNo; //온라인허가서번호
//    private String lstUpdDe; //최종수정일시
//    private String masterLinkCnt; //허가건수

@Data
public class RoadOcuuCertiData {

    @JsonProperty("rqsNo")
    private String rqsNo; //신청번호

    @JsonProperty("prmCd")
    private String prmCd; //인허가코드

    @JsonProperty("prmNm")
    private String prmNm; //인허가명

    @JsonProperty("orcd")
    private String orcd; //기관코드

    @JsonProperty("ornm")
    private String ornm; //기관명

    @JsonProperty("lgnId")
    private String lgnId; //로그인ID

    @JsonProperty("edDt")
    private String edDt; //종료일자

    @JsonProperty("edTyCd")
    private String edTyCd; //종료유형코드

    @JsonProperty("edRsn")
    private String edRsn; //종료사유

    @JsonProperty("feeCts")
    private String feeCts; //수수료내용

    @JsonProperty("perNo")
    private String perNo; //허가번호

    @JsonProperty("pmsnRectMtdCd")
    private String pmsnRectMtdCd; //허가서수령방법코드

    @JsonProperty("rlAppiNm")
    private String rlAppiNm; //실제신청자명

    @JsonProperty("onlPmsnNo")
    private String onlPmsnNo; //온라인허가서번호

    @JsonProperty("lstUpdDe")
    private String lstUpdDe; //최종수정일시

    @JsonProperty("masterLinkCnt")
    private String masterLinkCnt; //허가건수

}
