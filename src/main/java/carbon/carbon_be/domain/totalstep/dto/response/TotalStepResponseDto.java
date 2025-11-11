package carbon.carbon_be.domain.totalstep.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalStepResponseDto {
   private long totalStepCount;
}
