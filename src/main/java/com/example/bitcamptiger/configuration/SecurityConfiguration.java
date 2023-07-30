package com.example.bitcamptiger.configuration;

import com.example.bitcamptiger.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public static PasswordEncoder passwordEncoder(){


        return new BCryptPasswordEncoder();
    }


    //    필터 체인 구현(HttpSecurity 객체 사용)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{




        return http
//              csrf 공격에 대한 옵션 꺼두기
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(httpSecurityHttpBasicConfigurer -> {
                    httpSecurityHttpBasicConfigurer.disable();
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }).
//                토큰방식을 사용하기 때문에 세션방식 사용하지 않도록 설정
        authorizeHttpRequests((authorizaRequests)->{
//                    /요청은 모든 사용자가 이용가능
    authorizaRequests.requestMatchers("/","/member/**","/board/**").permitAll();
    authorizaRequests.anyRequest().authenticated();
})
//                로그인 로그아웃 설정
//                AuthenticationProvider에게 전달할
//                사용자 입력 아이디,비밀번호에 대한 UsernamePasswordToken을 전달하는
//                상태까지 설정
//              filter 등록
//                매요청마다 corsfilter 실행 후 jwtAuthenticationFilter 실행
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
                .build();


    }



}
