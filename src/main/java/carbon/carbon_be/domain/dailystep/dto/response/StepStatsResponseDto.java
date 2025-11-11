package carbon.carbon_be.domain.dailystep.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepStatsResponseDto {

   private long totalStepCount; // 총 걸음수
   private long periodStepSum;  // 기간 합계 걸음수
   private List<DailyStepData> dailySteps; // 날짜별로 나눠서 사용하도록

   @Getter
   @Setter
   @NoArgsConstructor
   @AllArgsConstructor
   @Builder
   public static class DailyStepData {
        private LocalDate date;
        private int stepCount;
   }
}
