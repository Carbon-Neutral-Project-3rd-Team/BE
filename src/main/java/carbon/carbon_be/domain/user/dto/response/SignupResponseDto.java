package carbon.carbon_be.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDto {

   private Long id;
   private String email;
   private String username;
   private LocalDateTime createdAt;
}
