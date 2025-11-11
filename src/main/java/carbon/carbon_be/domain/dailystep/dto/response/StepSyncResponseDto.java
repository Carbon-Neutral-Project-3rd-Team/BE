package carbon.carbon_be.domain.dailystep.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepSyncResponseDto {
    private int todayStepCount;
    private long totalStepCount;
}