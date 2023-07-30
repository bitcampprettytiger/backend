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
public class MemberDTO {
    private Long id;

    @Email(message = "이메일 형식으로 입력하세요")
//    @Pattern()
    private String username;

    @Size(min = 8,message = "8자 이상입력하세요.")
    private String password;

    private String role;

    private String token;
    public Member toMemberEntity(){
        return Member.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
