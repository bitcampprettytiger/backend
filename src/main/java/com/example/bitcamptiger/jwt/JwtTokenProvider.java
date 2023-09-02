package com.example.bitcamptiger.jwt;

import com.example.bitcamptiger.common.exception.BaseException;
import com.example.bitcamptiger.jwt.entity.RefreshToken;
import com.example.bitcamptiger.jwt.repository.RefreshTokenRepository;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.service.Impl.UserDetailsServiceImpl;
import com.example.bitcamptiger.response.BaseResponseStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final RefreshTokenRepository refreshTokenRepository;

//    Jwt Token의 signature의 유효성 검사에 사용될 키 값
//    BASE64 인코딩된 값
//    navercloud5todobootappgogibitcamp702
    private static final String SECRET_KEY ="bmF2ZXJjbG91ZDV0b2RvYm9vdGFwcGdvZ2liaXRjYW1wNzAy";
    private static final String RE_KEY ="bmF2ZXJjbG91ZDV0b2RvYm9vdGFwcGdvZ2liaXRjYW1wNzAy";

    //    SCERET_KEY를 Key 객체로 변환
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
//    Key key2 = Keys.hmacShaKeyFor(RE_KEY.getBytes(StandardCharsets.UTF_8));

//   *
//        사용자 정보를 받아서 JWT Token을 생성해주는 메소드
//        JSON 문자열을 Base64 인코딩하고 뒷부분에 서버에 있는 시그니쳐(SECRET_KEY)를 Hashing 해서 추가
//
    public String create(Member member){
//        토큰 만료일 설정. 현재로부터 1일뒤로 설정
        Date expireDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

//       JWT Token 생성하여 반환
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
    public String accessToken(Member member){
//        토큰 만료일 설정. 현재로부터 1일뒤로 설정
        Date expireDate = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));

//       JWT Token 생성하여 반환
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
                .claim("scope", "refresh")
//               토큰발행
                .compact();
    }


    //   JWT Token의 유효성 확인하느 메소드
//    subject에 담겨있는 username을 리턴한다.
    public String validateAndGetUsername(String token){
//        System.out.println(token);
        System.out.println("여기서 다 조사합니다.");
//        받아온 토큰 값을 파싱해서 유효성 검사
//        토큰에 있는 시그니쳐와 서버에서 가지고있는 시그니쳐값 비교
        System.out.println("validateAndGetUsername"+token);
        Claims claims
                 = Jwts.parserBuilder()
 //                시그니쳐에 담겨있느 토큰의값이랑 시크릿 키랑 비교
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.println(claims.getSubject());

        String scope = claims.get("scope", String.class);

        if ("refresh".equals(scope)) {
            String username = claims.getSubject();
//            UserDetails userDetails =
//                    userDetailsServiceImpl.loadUserByUsername(username);
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByName(username);
            if(refreshToken.isEmpty()){
                throw new BaseException(BaseResponseStatus.FAIL_LOGIN_REFRESH);
            }
            Claims refreshclaim
                    = Jwts.parserBuilder()
                    //                시그니쳐에 담겨있느 토큰의값이랑 시크릿 키랑 비교
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
//            refreshclaim.getSubject();
            if(refreshclaim.getExpiration().before(new Date())){
                throw new BaseException(BaseResponseStatus.EMPTY_JWT);
            }
            String accessToken = recreationAccessToken(username);

            return accessToken;

//           return refreshToken
//
//            throw new BaseException(BaseResponseStatus.FAIL_LOGIN_REFRESH);
//            return null;
        }
        else if ("access".equals(scope)) {
            try {
                // 검증
                //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
                System.out.println("11111111111111111111"+claims.getExpiration().before(new Date()));
                if (claims.getExpiration().before(new Date())) {
                    String subject = claims.getSubject();
                    Optional<RefreshToken> refreshToken = refreshTokenRepository.findByName(subject);
                    if(refreshToken.isEmpty()){
                        throw new BaseException(BaseResponseStatus.FAIL_LOGIN_REFRESH);
                    }
                    Claims refreshclaim
                            = Jwts.parserBuilder()
                            //                시그니쳐에 담겨있느 토큰의값이랑 시크릿 키랑 비교
                            .setSigningKey(SECRET_KEY.getBytes())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
//            refreshclaim.getSubject();
                    if(refreshclaim.getExpiration().before(new Date())){
                        System.out.println("refreshclaim.getExpiration().before(new Date())");
                        throw new BaseException(BaseResponseStatus.EMPTY_JWT);
                    }
                    String accessToken = recreationAccessToken(subject);

                    return accessToken;
                }

                return claims.getSubject();
            }catch (Exception e) {
                System.out.println("e.getMessage()"+e.getMessage());
                //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
                return null;
            }
        }
        return null;
    }
    public String validateRefreshToken(String refreshToken){

        System.out.println("refreshToken");
//        // refresh 객체에서 refreshToken 추출
//        String refreshToken = refreshTokenObj;

        Claims claims
                =Jwts.parserBuilder()
                .setSigningKey(RE_KEY.getBytes())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        try {
            // 검증
            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (claims.getExpiration().before(new Date())) {
                return recreationAccessToken(claims.getSubject().toString());
            }
            return null;
        }catch (Exception e) {

            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
            return null;
        }

    }
//  리플레시 토큰이 만료되지않을때 에센스 토큰 다시만들기1
    public String recreationAccessToken(String membername){

//        Claims claims = Jwts.claims().setSubject(userEmail); // JWT payload 에 저장되는 정보단위
//        Date now = new Date();
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        //Access Token
        String accessToken = Jwts.builder()
//                시그니쳐(서명) 부분에 들어갈 key값 지정
                .signWith(key, SignatureAlgorithm.HS256)
//               페이로드에 들어갈 내용
//                토큰의 주인(sub)
                .setSubject(membername)
//                토큰 발행주체(iss)
//                임의로 지정
                .setIssuer("todo boot app")
//                토큰 발행일자(isa)
                .setIssuedAt(new Date())
//                토큰 만료일자
                .setExpiration(expireDate)
//               토큰발행
                .compact();

        return accessToken;
    }

}
