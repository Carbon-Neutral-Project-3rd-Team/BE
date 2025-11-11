package carbon.carbon_be.domain.dailystep.service;

import carbon.carbon_be.domain.dailystep.dto.request.StepSyncRequestDto;
import carbon.carbon_be.domain.dailystep.dto.response.StepSyncResponseDto;
import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.dailystep.repository.DailyStepRepository;
import carbon.carbon_be.domain.totalstep.entity.TotalStep;
import carbon.carbon_be.domain.totalstep.repository.TotalStepRepository;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StepService {

   private final DailyStepRepository dailyStepRepository;
   private final TotalStepRepository totalStepRepository;
   private final UserRepository userRepository;

   @Transactional
   public StepSyncResponseDto syncSteps(Long userId, StepSyncRequestDto req) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

      LocalDate today = req.getRecordDate();
      int newStepCount = req.getStepCount();

      // 오늘의 걸음수 기록을 update, insert
      DailyStep dailyStep = dailyStepRepository.findByUserAndRecordDate(user, today)
          .orElseGet(() -> DailyStep.builder()
              .user(user)
              .recordDate(today)
              .stepCount(0)
              .build());

      int previousCount = dailyStep.getStepCount();
      int delta = newStepCount - previousCount;

      if (delta > 0) {
         dailyStep.updateStepCount(newStepCount);
         dailyStepRepository.save(dailyStep);

         // [2] 누적 걸음수 갱신
         TotalStep totalStep = totalStepRepository.findByUser(user)
             .orElseGet(() -> TotalStep.builder()
                 .user(user)
                 .totalStepCount(0L)
                 .build());
         totalStep.addSteps(delta);
         totalStepRepository.save(totalStep);
      }

      return StepSyncResponseDto.builder()
          .todayStepCount(dailyStep.getStepCount())
          .totalStepCount(totalStepRepository.findByUser(user)
              .map(TotalStep::getTotalStepCount)
              .orElse(0L))
          .build();

   }
}