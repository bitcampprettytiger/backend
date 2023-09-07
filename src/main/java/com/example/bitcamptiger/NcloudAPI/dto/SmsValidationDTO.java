package com.example.bitcamptiger.NcloudAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsValidationDTO {

    private String tel;
    private int code;
}
