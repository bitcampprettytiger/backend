package com.example.bitcamptiger.configuration;

import com.example.bitcamptiger.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
//Security filterchain을 구성하기 위한 어노테이션
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

//   비밀번호 암호화를 위한 PasswordEncoder
//    복호화가 불가능. match라는 메소드를 이용해서 사용자의 입력값과 DB의 저장값을 비교 =>
//    true false 리턴, match(암호화되지 않은 갑스 암호화된 값)


    @Bean
    public static PasswordEncoder passwordEncoder() {


        return new BCryptPasswordEncoder();
    }


    //    필터 체인 구현(HttpSecurity 객체 사용)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        return http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
//              csrf 공격에 대한 옵션 꺼두기
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(httpSecurityHttpBasicConfigurer -> {
                    httpSecurityHttpBasicConfigurer.disable();
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }).

//                토큰방식을 사용하기 때문에 세션방식 사용하지 않도록 설정
        authorizeHttpRequests((authorizaRequests) -> {
//                    /요청은 모든 사용자가 이용가능
//
//  authorizaRequests.requestMatchers("/board/**", "/menu/**", "/cart/**", "/reviews/**", "/orders/**","/upload/**").hasAuthority("ROLE_BASIC");
//  authorizaRequests.requestMatchers("/vendor/**","/reviews/**").hasAuthority("ROLE_BASIC");
//  authorizaRequests.requestMatchers("/**").hasAuthority("ROLE_VENDOR");
//  authorizaRequests.requestMatchers("/api/**").hasAuthority("ROLE_BASIC");
    authorizaRequests.requestMatchers("/api/**","/member/**","/vendor/join","/API/validateByRegion","/vendor/info","/reviews/**","/logout","/test-api-local.com/api/swagger-ui/index.html","/auth/**","/API/**","/API/dongJak/DongJakData","/API/gangseogu/GangseoguData","/API/dongdaemun/DongdaemunData","/auth2/**","/swagger/index.html",
    "/API/gyeonggi/GyeonggiData","/API/gangNam/GangNamData","/API/dobonggu/DobongguData","/API/dongJak/validateDongJak","/API/dobonggu/**","/API/gangNam/validateGangNam","/API/gyeonggi/validateGyeonggi","/API/dongdaemun/**","/API/gangseogu/validateGangseo","/API/dongJak/NoRyangJin","/app/users/auth2/google/login/callback","/api/callback","/app/users/auth2/GOOGLE/login/callback","/app/users/auth2/**","/swagger-ui.html").permitAll();
    authorizaRequests.requestMatchers("/board/**", "/menu/**", "/cart/**", "/reviews/**", "/orders/**","/upload/**","/api/**","/refresh","/myPage/**","/refresh/**","/favorite-Test/**","/businessApi/**","/payment/**").hasAnyAuthority("ROLE_VENDOR","ROLE_BASIC");
    authorizaRequests.requestMatchers("/vendor/**").hasAnyAuthority("ROLE_VENDOR","ROLE_BASIC");

    authorizaRequests.anyRequest().authenticated();



//
//    authorizaRequests.requestMatchers("/member/**").permitAll();
//    authorizaRequests.requestMatchers("/board/**", "/menu/**", "/cart/**", "/reviews/**", "/orders/**","/upload/**").permitAll();
//    authorizaRequests.requestMatchers("/favorite-Test/**","/businessApi/**","/API/**").permitAll();
//    authorizaRequests.requestMatchers("/vendor/**","/reviews/**").permitAll();
////     authorizaRequests.requestMatchers("/**").hasAuthority("ROLE_VENDOR");
////    authorizaRequests.requestMatchers("/api/**").hasAuthority("ROLE_BASIC");
//    authorizaRequests.requestMatchers("/refresh/**").permitAll();
//    authorizaRequests.requestMatchers("/refresh").permitAll();
//    authorizaRequests.requestMatchers("/api/**").permitAll();
//    authorizaRequests.anyRequest().authenticated();
////
//     authorizaRequests.requestMatchers("/board/**", "/menu/**", "/cart/**", "/reviews/**", "/orders/**","/upload/**").hasAuthority("ROLE_BASIC");
//     authorizaRequests.requestMatchers("/favorite-Test/**","/businessApi/**","/API/**").hasAuthority("ROLE_BASIC");
//
//     authorizaRequests.requestMatchers("/vendor/**","/reviews/**").hasAuthority("ROLE_BASIC");
////     authorizaRequests.requestMatchers("/**").hasAuthority("ROLE_VENDOR");
//     authorizaRequests.requestMatchers("/api/**").hasAuthority("ROLE_BASIC");

})
//                로그인 로그아웃 설정
//                AuthenticationProvider에게 전달할
//                사용자 입력 아이디,비밀번호에 대한 UsernamePasswordToken을 전달하는
//                상태까지 설정
//              filter 등록
//                매요청마다 corsfilter 실행 후 jwtAuthenticationFilter 실행
                .build();

    }


}
