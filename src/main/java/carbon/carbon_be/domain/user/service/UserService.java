package carbon.carbon_be.domain.user.service;

import carbon.carbon_be.domain.user.dto.request.SignupRequestDto;
import carbon.carbon_be.domain.user.dto.response.SignupResponseDto;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.entity.UserRole;
import carbon.carbon_be.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final이 붙은 필드의 생성자 생성
public class UserService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

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
          .username(signupRequestDto.getName())
          .role(UserRole.USER)
          .build();

      // 저장
      User saved = userRepository.save(user);

      return new SignupResponseDto(saved.getId(), saved.getEmail(), saved.getUsername());
   }
}
