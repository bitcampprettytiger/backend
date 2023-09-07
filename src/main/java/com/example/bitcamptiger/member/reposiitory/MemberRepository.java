package com.example.bitcamptiger.member.reposiitory;

import com.example.bitcamptiger.common.entity.BaseEntity;
import com.example.bitcamptiger.common.entity.BaseEntity.State;
import com.example.bitcamptiger.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String Username);

    Optional<Member> findBynickname(String nickname);

    Optional<Member> findByNameAndTel(String name, Long tel);

    Optional<Member> findByName(String name);


    //카카오
    //Optional<Member> findByKakaoId(Long kakaoId);

}
