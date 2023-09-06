package com.example.bitcamptiger.jwt;

import com.example.bitcamptiger.jwt.entity.RefreshToken;
import com.example.bitcamptiger.jwt.repository.RefreshTokenRepository;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {


    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String SECRET_KEY ="bmF2ZXJjbG91ZDV0b2RvYm9vdGFwcGdvZ2liaXRjYW1wNzAy";
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));


    @Transactional
    public void login(MemberDTO memberDTO){

        RefreshToken refreshToken = RefreshToken.builder().refreshToken(memberDTO.getRefreshToken()).name(memberDTO.getUsername()).build();
        String loginUserEmail = refreshToken.getName();
        if(refreshTokenRepository.existsByName(loginUserEmail)){
            log.info("기존의 존재하는 refresh 토큰 삭제");
            refreshTokenRepository.deleteByName(loginUserEmail);
        }
        refreshTokenRepository.save(refreshToken);

    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken){

        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public String validateRefreshToken(String refreshToken){
        RefreshToken refreshToken1 = getRefreshToken(refreshToken).get();
        String createdAccessToken = jwtTokenProvider.validateRefreshToken(refreshToken1.getRefreshToken());

        return createdAccessToken;
    }


    public String createJwt(Member member){

        Date expireDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        return Jwts.builder()
//                시그니쳐(서명) 부분에 들어갈 key값 지정
                .signWith(key, SignatureAlgorithm.HS256)
//               페이로드에 들어갈 내용
//                토큰의 주인(sub)
                .setSubject(member.getUsername())
//                토큰 발행주체(iss)
//                임의로 지정
                .setIssuer("todo boot app")
//                토큰 발행일자(isa)
                .setIssuedAt(new Date())
//                토큰 만료일자
                .setExpiration(expireDate)
                .claim("role",member.getRole())
                .claim("scope", "access")

//               토큰발행
                .compact();
    }




//    public Map<String, String> createRefreshJson(String createdAccessToken){
//
//        Map<String, String> map = new HashMap<>();
//        if(createdAccessToken == null){
//
//            map.put("errortype", "Forbidden");
//            map.put("status", "402");
//            map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");
//
//
//            return map;
//        }
//        //기존에 존재하는 accessToken 제거
//
//
//        map.put("status", "200");
//        map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
//        map.put("accessToken", createdAccessToken);
//
//        return map;
//
//
//    }


}
