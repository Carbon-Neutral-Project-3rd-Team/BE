package com.example.carbonproject.jwt;

import com.example.carbonproject.dto.CustomUserDetails;
import com.example.carbonproject.entity.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//http요청 당 한번만 실행되도록 보장
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    //토큰 검증 메소드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);

            return;//조건 해당시 메소드 종료
        }

        System.out.println("authorizationHeader now ");

        //Bearer 부분 제거 후 순수 토큰만
        String token=authorizationHeader.split(" ")[1];

        //토큰 소멸 시간 확인 (try-catch로 ExpiredJwtException 처리)
        try {
            if (jwtUtil.isExpired(token)) {
                System.out.println("token is expired");
                return;
            }
        } catch (ExpiredJwtException e) {
            // 토큰이 만료되었을 경우, 사용자에게 401 UNAUTHORIZED 응답을 보내거나
            // filterChain.doFilter(request, response) 호출 후 return;
            System.out.println("ERROR: JWT Expired."+e.getMessage());

            // 만료된 토큰이 들어오면 SecurityContext에 아무것도 설정하지 않고 다음 필터로 넘겨
            // Spring Security가 401/403 응답을 처리하게 하거나,
            // 직접 401 응답을 반환할 수 있습니다.
            // 여기서는 다음 필터로 넘기지 않고 요청을 중단해야 합니다.
            filterChain.doFilter(request, response);
            return;
        }

        //토큰에서 username, role 얻음
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        //userEntity 생성 후 값 세팅
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername(username);
        userEntity.setRole(role);
        userEntity.setPassword("temppassword");


        //UserDetails에 회원 정보 객체
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);


        Authentication authToken=new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
