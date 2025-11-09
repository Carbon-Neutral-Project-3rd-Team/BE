package carbon.carbon_be.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

   @Value("${jwt.secret}")
   private String secretKey;

   @Value("${jwt.expire_ms}")
   private long accessTokenExpireMs;

   @Value("${jwt.refresh_expire_ms}")
   private long refreshTokenExpireMs;

   @PostConstruct
   public void init() {
      secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
   }

   public String createAccessToken(String email, Long userId) {

      // 1. ClaimsBuilder를 사용하여 Claims 생성 및 완성
      Claims claims = Jwts.claims().setSubject(email);
      claims.put("userId", userId);

      // 2. 시간 정보 설정
      Date now = new Date();
      // 현재 시간(now)에 만료 시간(accessTokenExpireMs)을 더하여 최종 만료 시간을 계산합니다.
      Date validity = new Date(now.getTime() + accessTokenExpireMs);

      // 3. 토큰 빌드 및 생성
      return Jwts.builder()
          .setClaims(claims)
          // 토큰 발행 시간 (Issued At) 설정
          .setIssuedAt(now)
          // 토큰 만료 시간 (Expiration) 설정
          .setExpiration(validity)
          // 서명
          .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
          .compact();
   }

   public String getEmail(String token) {
      return Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
   }

   public boolean validateToken(String token) {
      try {
         Jwts.parserBuilder()
             .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
             .build()
             .parseClaimsJws(token);
         return true;
      } catch (JwtException | IllegalArgumentException e) {
         return false;
      }
   }

   public String createRefreshToken(String email, Long userId) {
      Claims claims = Jwts.claims().setSubject(email);
      claims.put("userId", userId);

      Date now = new Date();
      Date validity = new Date(now.getTime() + refreshTokenExpireMs);

      return Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(now)
          .setExpiration(validity)
          .signWith(SignatureAlgorithm.HS256, secretKey)
          .compact();
   }
}
