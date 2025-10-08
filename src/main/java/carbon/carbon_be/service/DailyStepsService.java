package carbon.carbon_be.service;

import carbon.carbon_be.dto.StepsResponseDto;
import carbon.carbon_be.entity.DailySteps;
import carbon.carbon_be.repository.DailyStepsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyStepsService {

    private final DailyStepsRepository dailyStepsRepository;

    public StepsResponseDto getLatestSteps(Long userId) {
        DailySteps steps = dailyStepsRepository.findTopByUserIdOrderByRecordDateDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));
        return new StepsResponseDto(
                steps.getUser().getUsername(),
                steps.getRecordDate().toString(),
                steps.getStepCount()
        );
    }

}
