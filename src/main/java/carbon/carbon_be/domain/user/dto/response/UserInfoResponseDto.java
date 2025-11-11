package carbon.carbon_be.domain.user.dto.response;

import carbon.carbon_be.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponseDto {

   private Long id;
   private String email;
   private String name;
   private String role;

   public static UserInfoResponseDto from(User user) {
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
