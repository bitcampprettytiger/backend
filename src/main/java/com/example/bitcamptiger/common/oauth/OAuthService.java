package com.example.bitcamptiger.common.oauth;

import com.example.bitcamptiger.common.Constant;
import com.example.bitcamptiger.common.exception.BaseException;
import com.example.bitcamptiger.jwt.JwtService;
import com.example.bitcamptiger.jwt.JwtTokenProvider;
import com.example.bitcamptiger.member.dto.GetSocialMemberDto;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.model.GoogleOAuthToken;
import com.example.bitcamptiger.member.model.GoogleUser;
import com.example.bitcamptiger.member.model.PostUserRes;
import com.example.bitcamptiger.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.example.bitcamptiger.response.BaseResponseStatus.INVALID_OAUTH_TYPE;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;
    private final MemberService userService;
    private final JwtService jwtService;


    public  void accessRequest(Constant.SocialLoginType socialLoginType) throws IOException {
        String redirectURL;
        switch (socialLoginType){ //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스이다.
            case GOOGLE:{
                redirectURL= googleOauth.getOauthRedirectURL();
            }break;
            default:{
                throw new BaseException(INVALID_OAUTH_TYPE);
            }

        }

        response.sendRedirect(redirectURL);
    }


    public GetSocialMemberDto oAuthLoginOrJoin(Constant.SocialLoginType socialLoginType, String code) throws IOException {

        switch (socialLoginType) {
            case GOOGLE: {
                //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
                ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToken(code);
                //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
                GoogleOAuthToken accessToken = googleOauth.getAccessToken(accessTokenResponse);

                //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
                ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(accessToken);
                //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
                GoogleUser userInfo = googleOauth.getUserInfo(userInfoResponse);







                //우리 서버의 db와 대조하여 해당 user가 존재하는 지 확인한다.
                if(userService.checkUserByEmail(userInfo.getEmail())) { // user가 DB에 있다면, 로그인 진행
                    // 유저 정보 조회
                    Member getUserRes = userService.getUserByEmail(userInfo.getEmail());

                    //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
                    String jwtToken = jwtService.createJwt(getUserRes);

                    //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
                    GetSocialMemberDto getSocialOAuthRes = new GetSocialMemberDto(jwtToken, getUserRes.getId(), accessToken.getAccess_token(), accessToken.getToken_type());
                    return getSocialOAuthRes;
                }


                else { // user가 DB에 없다면, 회원가입 진행
                    // 유저 정보 저장
                    PostUserRes postUserRes = userService.createOAuthUser(userInfo.toEntity());
//                    googleUser.
                    GetSocialMemberDto getSocialOAuthRes = new GetSocialMemberDto(postUserRes.getJwt(), postUserRes.getId(), accessToken.getAccess_token(), accessToken.getToken_type());
                    return getSocialOAuthRes;
                }
            }
            case KAKAO: {

            }
            case NAVER: {

            }
            case APPLE:{

            }

            default: {
                throw new BaseException(INVALID_OAUTH_TYPE);
            }

        }
    }


}
