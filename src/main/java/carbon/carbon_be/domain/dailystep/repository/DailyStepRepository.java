package carbon.carbon_be.domain.dailystep.repository;

import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyStepRepository extends JpaRepository<DailyStep, Long> {
   Optional<DailyStep> findByUserAndRecordDate(User user, LocalDate date);
}
