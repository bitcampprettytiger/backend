package com.example.bitcamptiger.vendor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
public class VendorValidationDto {

    @JsonProperty("b_no")
    private String b_no;       // 사업자번호-필수

    @JsonProperty("start_dt")
    private String start_dt;   // 개업일자-필수

    @JsonProperty("p_nm")
    private String p_nm;       // 대표자명-필수

    @JsonProperty("p_nm2")
    private String p_nm2;      // 대표자명2

    @JsonProperty("b_nm")
    private String b_nm;       // 업체명

    @JsonProperty("corp_no")
    private String corp_no;    // 법인번호

    @JsonProperty("b_sector")
    private String b_sector;   // 주업태명

    @JsonProperty("b_type")
    private String b_type;     // 주종목명


    public VendorValidationDto(String b_no, String start_dt, String p_nm) {
        this.b_no = b_no;
        this.start_dt = start_dt;
        this.p_nm = p_nm;
    }


}