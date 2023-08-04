package com.example.bitcamptiger.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BusinessDataDto {
    @JsonProperty("b_no")
    private String b_no;

    @JsonProperty("b_stt")
    private String b_stt;

    @JsonProperty("b_stt_cd")
    private String b_stt_cd;

    @JsonProperty("tax_type")
    private String tax_type;

    @JsonProperty("tax_type_cd")
    private String tax_type_cd;

    @JsonProperty("end_dt")
    private String end_dt;

    @JsonProperty("utcc_yn")
    private String utcc_yn;

    @JsonProperty("tax_type_change_dt")
    private String tax_type_change_dt;

    @JsonProperty("invoice_apply_dt")
    private String invoice_apply_dt;

    @JsonProperty("rbf_tax_type")
    private String rbf_tax_type;

    @JsonProperty("rbf_tax_type_cd")
    private String rbf_tax_type_cd;
}

