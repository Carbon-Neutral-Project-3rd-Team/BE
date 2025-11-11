package carbon.carbon_be.domain.user.service;

import carbon.carbon_be.domain.user.dto.request.LoginRequestDto;
import carbon.carbon_be.domain.user.dto.request.SignupRequestDto;
import carbon.carbon_be.domain.user.dto.response.LoginResponseDto;
import carbon.carbon_be.domain.user.dto.response.SignupResponseDto;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.entity.UserRole;
import carbon.carbon_be.domain.user.repository.UserRepository;
import carbon.carbon_be.global.security.CustomUserDetails;
import carbon.carbon_be.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙은 field의 생성자 생성
public class AuthService {


   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authenticationManager;
   private final JwtTokenProvider jwtTokenProvider;


   public SignupResponseDto signup(SignupRequestDto signupRequestDto){

      // 이메일 중복 확인
      if (userRepository.existsByEmail(signupRequestDto.getEmail())){
         throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
      }

      // password 암호화
      String encodePassword = passwordEncoder.encode(signupRequestDto.getPassword());

      // 엔티티 생성
      User user = User.builder()
          .email(signupRequestDto.getEmail())
          .password(encodePassword)
          .username(signupRequestDto.getUsername())
          .role(UserRole.USER)
          .build();

      // 저장
      User saved = userRepository.save(user);

      return new SignupResponseDto(saved.getId(), saved.getEmail(), saved.getUsername(),saved.getCreatedAt());
   }


    public LoginResponseDto login(LoginRequestDto request) {
        // 1) 이메일/비번 검증 (Spring Security에게 맡김)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();

        // 2) JWT 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail(), user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail(), user.getId());

        return new LoginResponseDto(accessToken, refreshToken);
    }
}
