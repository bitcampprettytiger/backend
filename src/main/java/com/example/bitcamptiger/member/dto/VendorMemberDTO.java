package com.example.bitcamptiger.member.dto;

import com.example.bitcamptiger.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorMemberDTO {

    @Email(message = "이메일 형식으로 입력하세요")
    //  아이디
    private String username;

    //  비밀번호
    @Size(min = 8,message = "8자 이상입력하세요.")
    private String password;

    //  휴대폰 번호
    private Long tel;

    //  개인정보 동의
    private boolean privacy;

    //  별명
    private String nickname;

    //  판매자,관리자,사용자
    private String role;


    //  소셜로그인 인가 로컬로그인인가
    private boolean isOAuth = false;

    //  계정의 만료여부 조회 휴면계정
    private boolean AccountNonExpired;

    //  계정의 차단 여부 조회
    private boolean NonLocked;

    private String accessToken;

    private String refreshToken;


    public Member toMemberEntity(){
        return Member.builder()
                .username(this.username)
                .password(this.password)
                .nickname(this.nickname)
                .tel(this.tel)
                .role("ROLE_VENDOR")
                .privacy(this.privacy)
                .isOAuth(false)
                .type("local")
                .build();
    }
}
