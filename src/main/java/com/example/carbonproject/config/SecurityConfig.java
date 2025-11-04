package com.example.carbonproject.config;

import com.example.carbonproject.jwt.JWTFilter;
import com.example.carbonproject.jwt.JWTUtil;
import com.example.carbonproject.jwt.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.jwtUtil=jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors((corsCustomizer -> corsCustomizer
                        .configurationSource(new CorsConfigurationSource() {

                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                                CorsConfiguration configuration = new CorsConfiguration();

                                configuration.setAllowedOrigins(Collections.singletonList("*"));
                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                configuration.setAllowCredentials(true);
                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                configuration.setMaxAge(3600L);

                                configuration.setExposedHeaders(Collections.singletonList("*"));

                                return configuration;
                            }
                        })));

        //csrf disable설정
        http
                .csrf((auth)->auth.disable());

        //form 로그인 방식 disable
        http
                .formLogin((auth)->auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth)->auth.disable());

        // 1. AuthenticationManager 획득 (LoginFilter 생성에 필요)
        AuthenticationManager authenticationManager = authenticationManager(authenticationConfiguration);

        // 2. LoginFilter 인스턴스 생성
        LoginFilter loginFilter = new LoginFilter(authenticationManager, jwtUtil);

        // ✅ 필수 추가: LoginFilter가 처리할 URL 명시
        loginFilter.setFilterProcessesUrl("/loginProc");

        // 3. 인가 작업 (인가 목록에 /loginProc 포함)
        http
                .authorizeHttpRequests((auth)->{auth
                        .requestMatchers("/login","/","/join", "/loginProc").permitAll() // ✅ /loginProc을 permitAll에 포함
                        .requestMatchers("/admin","/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated();//로그인 사용자만 접근 가능 설정
                });

        // 4. JWTFilter 등록 (LoginFilter보다 먼저 실행)
        http
                .addFilterBefore(new JWTFilter(jwtUtil),LoginFilter.class);

        // 5. LoginFilter 등록
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        // session을 stateless상태로 관리
        http
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
