package carbon.carbon_be.domain.user.controller;

import carbon.carbon_be.domain.user.dto.response.UserInfoResponseDto;
import carbon.carbon_be.global.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

   @GetMapping("/user/info")
   public ResponseEntity<UserInfoResponseDto> getMyInfo(
       @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        // JwtAuthenticationFilter에서 검증된 사용자 정보
        return ResponseEntity.ok(
                UserInfoResponseDto.from(userDetails.getUser())
        );
    }
}
