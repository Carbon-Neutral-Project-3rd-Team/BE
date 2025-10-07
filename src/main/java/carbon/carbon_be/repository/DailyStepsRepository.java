package carbon.carbon_be.repository;

import carbon.carbon_be.entity.DailySteps;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface DailyStepsRepository extends JpaRepository<DailySteps, Long> {
    DailySteps findTopByUserIdOrderByRecordDateDesc(Long userId);
    // 그 유저의 최신 걸음 수 레코드 하나 가져오기
}
