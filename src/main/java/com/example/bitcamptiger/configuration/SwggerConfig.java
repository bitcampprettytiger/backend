package com.example.bitcamptiger.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;


//@Configuration
//@EnableWebMvc
//public class SwggerConfig {
//
//@Bean
//    public Doclet swaggerAPi(){
//
//    return new Docket(DocumentationType.OAS_30)
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.example.bitcamptiger.member.controller"))
//            .paths(PathSelectors.any())
//            .build()
//            .apiInfo(mySwaggerInfo());
//}
//private ApiInfo mySwaggerInfo(){
//
//    return new ApiInfoBuilder()
//            .title("swaggerTest API")
//            .description("SwaggerTest API Docs")
//            .build();
//}
//
//
//
//    }

@OpenAPIDefinition(
        info = @Info(title = "채팅서비스 API 명세서",
                description = "헥사고날 아키텍처 기반 채팅 서비스 API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/member/**","/vendor/**"};

        return GroupedOpenApi.builder()
                .group("채팅서비스 API v1")
                .pathsToMatch(paths)
                .build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**",
                "/favicon.ico",
                "/error",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**");
    }

}
