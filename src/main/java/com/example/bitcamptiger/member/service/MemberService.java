package com.example.bitcamptiger.member.service;

import com.example.bitcamptiger.NcloudAPI.dto.UpdatePasswordDTO;
import com.example.bitcamptiger.common.entity.BaseEntity;
import com.example.bitcamptiger.common.exception.BaseException;
import com.example.bitcamptiger.jwt.JwtService;
import com.example.bitcamptiger.jwt.RedisUtil;
import com.example.bitcamptiger.jwt.repository.RefreshTokenRepository;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.dto.VendorMemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.model.PostUserRes;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.response.BaseResponseStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.UUID;

import static com.example.bitcamptiger.common.entity.BaseEntity.State.ACTIVE;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final static int LENGTH_10_INT_RADIX = 6;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RedisUtil redisUtil;

    private final JwtService jwtService;

//    public Optional<Member> findByKakaoId(Long kakaoId) {
//        return memberRepository.findByKakaoId(kakaoId);
//    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }


    public MemberDTO join(MemberDTO member) {

        if (member == null || member.getUsername() == null) {
            throw new RuntimeException("Invalid Argument");
        }

        if (member.getNickname() == null) {
            String uuid = parseToShortUUID(UUID.randomUUID().toString().replace("-", ""));

            member.setNickname(uuid);
        }


        Member joinmember = member.toMemberEntity();

        //username 중복체크
        if (memberRepository.existsByUsername(member.getUsername())) {

            throw new RuntimeException("already exist username");
        }
        return memberRepository.save(joinmember).toMemberDTO();
    }


    public VendorMemberDTO vendorjoin(VendorMemberDTO member) {
        if (member == null || member.getUsername() == null) {
            throw new RuntimeException("Invalid Argument");
        }
        if (member.getNickname() == null) {
            String uuid = parseToShortUUID(UUID.randomUUID().toString().replace("-", ""));

            member.setNickname(uuid);
        }


        Member joinmember = member.toMemberEntity();

        //username 중복체크
        if (memberRepository.existsByUsername(member.getUsername())) {

            throw new RuntimeException("already exist username");
        }
        return memberRepository.save(joinmember).toVendorMemberDTO();
    }

    public Optional<Member> login(Member member) {

        Optional<Member> loginMember = memberRepository.findByUsername(member.getUsername());
        System.out.println(loginMember.get());
        if (loginMember.isEmpty()) {
            throw new RuntimeException("not exist username");
        }

//       비밀번호 비교
//        PasswordEncoder 객체 사용
//        PasswordEncoder의 matches(암호화되지 않은 비밀번호, 암호화된 비밀번호) 메소드 사용

        if (!passwordEncoder.matches(member.getPassword(), loginMember.get().getPassword())) {
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

    public static String parseToShortUUID(String uuid) {
        int l = ByteBuffer.wrap(uuid.getBytes()).getInt();
        return Integer.toString(l, LENGTH_10_INT_RADIX);
    }

    public String logout(String accessToken, Member users) {

        // refreshToken 테이블의 refreshToken 삭제
        refreshTokenRepository.deleteByName(users.getUsername());

        // 레디스에 accessToken 사용못하도록 등록
        redisUtil.setBlackList(accessToken, "accessToken", 5);

        return "로그아웃 완료";
    }


    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        Optional<Member> result = memberRepository.findByUsername(email);
        if (result.isPresent()) return true;
        return false;
    }

    public Member getUserByEmail(String email) {
        Member user = memberRepository.findByUsername(email).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        return new Member(user);
    }

    public PostUserRes createOAuthUser(Member user) {
        Member saveUser = memberRepository.save(user);
        if (user.getState() == BaseEntity.State.INACTIVE) {
            throw new BaseException(BaseResponseStatus.POST_INACTIVE);
        }

        String jwtToken = jwtService.createJwt(user);
        return new PostUserRes(saveUser.getId(), jwtToken);
    }

    public Optional<Member> findByUsername(String email) {
        return memberRepository.findByUsername(email);
    }

    public boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Optional<Member> memberOptional = memberRepository.findByNameAndTel(updatePasswordDTO.getName(), Long.valueOf(updatePasswordDTO.getTel()));

        if(memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setPassword(passwordEncoder.encode(updatePasswordDTO.getPassword()));
            memberRepository.save(member);

            return true;
        }

        return false;

    }
}
