package com.example.bitcamptiger.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetSocialMemberDto {
    private String jwtToken;
    private Long userId;
    private String accessToken;
    private String tokenType;
}
