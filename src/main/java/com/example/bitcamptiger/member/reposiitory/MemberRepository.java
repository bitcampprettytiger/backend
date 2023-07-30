package com.example.bitcamptiger.member.reposiitory;

import com.example.bitcamptiger.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String Username);

}
