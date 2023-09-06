package com.example.bitcamptiger.socialLogin.service;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.service.MemberService;
import com.example.bitcamptiger.socialLogin.dto.KakaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.restapi-key}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;


    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code";
    }

    public KakaoDTO getKakaoInfo(String code) throws Exception {
        // 코드가 null인 경우 예외 처리
        if (code == null) {
            throw new Exception("인증 코드를 가져오는 데 실패했습니다.");
        }

        String accessToken = "";
        String refreshToken = "";

        try {
            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            // HTTP 요청 파라미터 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", KAKAO_CLIENT_ID);
            params.add("client_secret", KAKAO_CLIENT_SECRET);
            params.add("code", code);
            params.add("redirect_uri", KAKAO_REDIRECT_URL);

            // RestTemplate을 사용하여 카카오에 액세스 토큰 요청
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            // HTTP 응답에서 액세스 토큰 및 리프레시 토큰 추출
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");
        } catch (Exception e) {
            throw new Exception("API 호출에 실패했습니다.");
        }

        // 액세스 토큰을 사용하여 카카오 사용자 정보 가져오기
        KakaoDTO kakaoInfo = getUserInfoWithToken(accessToken);

        // 기존 회원인지 확인
        Optional<Member> existingMember = memberService.findByUsername(kakaoInfo.getEmail());

        if (existingMember.isPresent()) {
            // 기존 회원이면 업데이트
            Member member = existingMember.get();
            member.setNickname(kakaoInfo.getNickname());
            member.setUsername(kakaoInfo.getEmail());
            memberService.save(member);
        } else {
            // 기존 회원이 아니면 신규 회원으로 저장
            Member newMember = Member.builder()
                    .username(kakaoInfo.getEmail())
                    .nickname(kakaoInfo.getNickname())
                    .password(passwordEncoder.encode(kakaoInfo.getNickname()))
                    .tel(01000001234)
                    .privacy(true)
                    .role("ROLE_VENDOR")
                    .type("local")
                    .build();
            memberService.save(newMember);
        }

        return kakaoInfo;
    }


    private KakaoDTO getUserInfoWithToken(String accessToken) throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

        long id = (long) jsonObj.get("id");
        String email = String.valueOf(account.get("email"));
        String nickname = String.valueOf(profile.get("nickname"));

        return KakaoDTO.builder()
                .id(id)
                .email(email)
                .nickname(nickname).build();
    }


}
