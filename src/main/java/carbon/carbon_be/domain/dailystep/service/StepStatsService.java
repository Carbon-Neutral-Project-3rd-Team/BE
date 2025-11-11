package carbon.carbon_be.domain.dailystep.service;

import carbon.carbon_be.domain.dailystep.dto.response.StepStatsResponseDto;
import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.dailystep.repository.DailyStepRepository;
import carbon.carbon_be.domain.totalstep.dto.response.TotalStepResponseDto;
import carbon.carbon_be.domain.totalstep.entity.TotalStep;
import carbon.carbon_be.domain.totalstep.repository.TotalStepRepository;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StepStatsService {

   private final DailyStepRepository dailyStepRepository;
   private final TotalStepRepository totalStepRepository;
   private final UserRepository userRepository;

   @Transactional(readOnly = true)
   public StepStatsResponseDto getWeeklyStats(Long userId) {
        return getStats(userId, 7);
   }

   @Transactional(readOnly = true)
   public StepStatsResponseDto getMonthlyStats(Long userId) {
        return getStats(userId, 30);
   }

   // 일주일, 30일 기간동안의 기록을 보기 위한 매서드
   private StepStatsResponseDto getStats(Long userId, int days) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(days - 1);

        List<DailyStep> steps = dailyStepRepository.findByUserAndDateRange(user, start, end);

        long periodSum = steps.stream()
                .mapToLong(DailyStep::getStepCount)
                .sum();

        long total = totalStepRepository.findByUser(user)
                .map(TotalStep::getTotalStepCount)
                .orElse(0L);

        List<StepStatsResponseDto.DailyStepData> list = steps.stream()
                .map(ds -> StepStatsResponseDto.DailyStepData.builder()
                        .date(ds.getRecordDate())
                        .stepCount(ds.getStepCount())
                        .build())
                .collect(Collectors.toList());

        return StepStatsResponseDto.builder()
                .totalStepCount(total)
                .periodStepSum(periodSum)
                .dailySteps(list)
                .build();
   }

   @Transactional(readOnly = true)
    public TotalStepResponseDto getTotalSteps(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        long total = totalStepRepository.findByUser(user)
                .map(TotalStep::getTotalStepCount)
                .orElse(0L);

        return TotalStepResponseDto.builder()
                .totalStepCount(total)
                .build();
    }
}
