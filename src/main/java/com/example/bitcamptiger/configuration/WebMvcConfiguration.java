package com.example.bitcamptiger.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value(value = "${file.resource}")
    private String filePath;

    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry){

        //'/upload/'로 시작하는 요청이오면 해당 요청에 대응하는  파일을 filePath에서 제공
        //실제 파일 시스템 경로를 설정. 클라이언트의 요청이 들어오면 해당 경로에서 정적 리소스를 찾아 제공.
        resourceHandlerRegistry.addResourceHandler("/upload/**")
                .addResourceLocations(filePath);


    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                //허용된 요청방식
//                .allowedMethods("GET","POST","PUT","DELETE")
//                //허용될 요청 헤더
//                .allowedHeaders("*")
//                //인증에 관한 정보 허용
//                .allowCredentials(true)
//                //타임아웃 시간 설정
//                .maxAge(3600);
//    }

}