package carbon.carbon_be.repository;

import carbon.carbon_be.entity.DailySteps;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface DailyStepsRepository extends JpaRepository<DailySteps, Long> {
    Optional<DailySteps> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);
}
