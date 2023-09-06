package com.example.bitcamptiger.member.model;

import com.example.bitcamptiger.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//구글(서드파티)로 액세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUser {
    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String givenName;
    public String familyName;
    public String picture;
    public String locale;

    public Member toEntity() {
        return Member.builder()
                .username(this.email)
                .password("NONE")
                .nickname(this.name)
                .isOAuth(true)
                .type("google")
                .role("ROLE_BASIC")
                .build();
    }
}
