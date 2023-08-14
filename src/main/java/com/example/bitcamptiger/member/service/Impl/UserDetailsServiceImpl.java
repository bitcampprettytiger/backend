package com.example.bitcamptiger.member.service.Impl;

import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
//import com.example.wantedpreonboardingbackend.member.entity.CustomUserDetails;
//import com.example.wantedpreonboardingbackend.member.entity.Member;
//import com.example.wantedpreonboardingbackend.member.reposiitory.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userOptional = memberRepository.findByUsername(username);

        if(userOptional.isEmpty()) {
            return null;
        }

        return CustomUserDetails.builder()
                .user(userOptional.get())
                .build();
    }
}
