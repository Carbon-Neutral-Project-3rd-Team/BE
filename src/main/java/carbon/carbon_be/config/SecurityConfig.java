package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //configuration class로 등록
@EnableWebSecurity //spring security에서 관리
public class SecurityConfig {

    @Bean//어디서든 호출하기 위해서 bean으로 등록
    public BCryptPasswordEncoder bCryptPasswordEncoder() {//로그인 시 비밀번호에 대한 단방향 해시 암호화 진행 -> 회원 가입 시 비밀번호 항목 암호화

        return new BCryptPasswordEncoder();
    }


    @Bean//객체를 만들고 관리하도록
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {//예외처리
        http
                .authorizeHttpRequests((auth)->auth //요청 허용 및 거부 - 무조건 람다식으로 구현(인가 설정)
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                        .requestMatchers("/my/**").hasAnyRole("USER","ADMIN")   //**: wildcard
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated() // 위에서 처리하지 못한 것들 -> anyRest로 처리,
                );

        http   //admin에서도 login 페이지 설정
                .formLogin((auth)->auth.loginPage("/login")//인증되지 않은 사용자가 접속(여기선 admin) 시 login으로 리다이렉션
                        .loginProcessingUrl("/loginProc")// login의 폼데이터가 Post요청으로 전송 된 후 인증 처리 수행
                        .permitAll() //로그인 하지 않은 상태에서도 로그인 페이지 접근 허용
                );

//        http  //csrf 주석 처리 -> 기본 default 값으로 -> enable하기 위한 설정 필요
//                .csrf((auth)->auth.disable());

        http
                .sessionManagement((auth)->auth
                        .maximumSessions(1)//하나의 아이디에서 동시접속 중복 로그인 수 설정
                        .maxSessionsPreventsLogin(true));//상단의 로그인 제한을 초과햇을 경우 -> 로그인 금지시킬껀지
                        //true->새로운 로그인 차단 , false -> 기존 세션 하나 삭제후 새 로그인 진행
        http
                .sessionManagement((auth)->auth
                        .sessionFixation().changeSessionId()//세션 고정 보호
                );

        return http.build();
    }
}
