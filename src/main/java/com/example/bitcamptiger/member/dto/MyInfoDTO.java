package com.example.bitcamptiger.member.dto;

import com.example.bitcamptiger.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyInfoDTO {

    private Long id;

    private String username;

    private String password;

    private String role;

    private String nickName;


}
