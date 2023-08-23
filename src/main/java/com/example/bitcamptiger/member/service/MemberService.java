package com.example.bitcamptiger.member.service;

import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.dto.VendorMemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO join(MemberDTO member ) {
        if(member == null || member.getUsername() ==null){
            throw new RuntimeException("Invalid Argument");
        }


       Member joinmember = member.toMemberEntity();

        //username 중복체크
        if(memberRepository.existsByUsername(member.getUsername())){

            throw new RuntimeException("already exist username");
        }
        return memberRepository.save(joinmember).toMemberDTO();
    }


    public VendorMemberDTO vendorjoin(VendorMemberDTO member ) {
        if(member == null || member.getUsername() ==null){
            throw new RuntimeException("Invalid Argument");
        }


        Member joinmember = member.toMemberEntity();

        //username 중복체크
        if(memberRepository.existsByUsername(member.getUsername())){

            throw new RuntimeException("already exist username");
        }
        return memberRepository.save(joinmember).toVendorMemberDTO();
    }
    public Optional<Member> login(Member member) {
        System.out.println(member.getUsername());
        Optional<Member> loginMember = memberRepository.findByUsername(member.getUsername());
        System.out.println(loginMember.get());
        if(loginMember.isEmpty()){
            throw new RuntimeException("not exist username");
        }

//       비밀번호 비교
//        PasswordEncoder 객체 사용
//        PasswordEncoder의 matches(암호화되지 않은 비밀번호, 암호화된 비밀번호) 메소드 사용

        if(!passwordEncoder.matches(member.getPassword(),loginMember.get().getPassword())){
            throw new RuntimeException("wrong Password");
        }
        return memberRepository.findByUsername(member.getUsername());
    }


    public MemberDTO updatemember(MemberDTO member) {

        Member findmember = memberRepository.findByUsername(member.getUsername()).orElseThrow(EntityNotFoundException::new);

        findmember.setNickname(member.getNickname());

        MemberDTO memberDTO = findmember.toMemberDTO();

        return memberDTO;

    }

    public void deletemember(MemberDTO member) {

        Member findmember = memberRepository.findByUsername(member.getUsername()).orElseThrow(EntityNotFoundException::new);

        memberRepository.delete(findmember);
    }
}
