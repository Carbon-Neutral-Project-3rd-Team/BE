package com.example.carbonproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    //cors 귳칙 등록 메서드 재정의
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){

        corsRegistry.addMapping("/**")//서버의 모든  url경로
                .allowedHeaders("*")//모든 헤더 허용
                .allowedMethods("*")//모든 http매서드 허용
                .allowCredentials(true)//인증정보 요청에 포함
                .exposedHeaders("Authorization","Location")//서버가 응답으로 보낸 authorization 헤더를 브라우저가 읽을 수 있도록
                .allowedOrigins("https://localhost:3000");//프론트 개발서버에서 서버로 욫청 보내는 것 허용

    }
}
