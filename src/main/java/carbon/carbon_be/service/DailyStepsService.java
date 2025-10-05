package carbon.carbon_be.service;

import carbon.carbon_be.entity.DailySteps;
import carbon.carbon_be.repository.DailyStepsRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class DailyStepsService {
    private final DailyStepsRepository repository;

    public DailyStepsService(DailyStepsRepository repository) {
        this.repository = repository;
    }

    public DailySteps getSteps(Long userId, LocalDate date) {
        return repository.findByUserIdAndRecordDate(userId, date)
                .orElseThrow(() -> new RuntimeException("데이터가 없습니다."));
    }
}
