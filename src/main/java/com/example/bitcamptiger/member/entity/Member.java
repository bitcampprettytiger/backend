package com.example.bitcamptiger.member.entity;

import com.example.bitcamptiger.member.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "T_MEMBER", // 테이블 이름을 대문자로 지정
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@SequenceGenerator(
        name = "MemberSeqGenerator",
        sequenceName = "T_MEMBER_SEQ", // 시퀀스 이름을 대문자로 지정
        initialValue = 1,
        allocationSize = 1
)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false)
    private String username;



    @Column(nullable = false)
    private String password;


//  별명
    private String nickname;


//  판매자,관리자,사용자.
    private String role;

//  가입 타입
    private String local;



    public MemberDTO toMemberDTO() {
        return MemberDTO.builder()
                .username(this.username)
                .password("") // 비밀번호는 빈 문자열로 설정 (DTO에 비밀번호를 담지 않기 위함)
                .role(this.role)
                .build();
    }
}
