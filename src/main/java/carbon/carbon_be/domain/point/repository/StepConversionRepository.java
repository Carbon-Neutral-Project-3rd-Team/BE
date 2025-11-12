package carbon.carbon_be.domain.point.repository;

import carbon.carbon_be.domain.point.entity.StepConversion;
import carbon.carbon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StepConversionRepository extends JpaRepository<StepConversion, Long> {

    List<StepConversion> findByUserAndConversionDate(User user, LocalDate date);

    @Query("SELECT COALESCE(SUM(s.convertedSteps), 0) FROM StepConversion s " +
            "WHERE s.user = :user AND s.conversionDate = :date")
    int sumConvertedStepsByUserAndDate(User user, LocalDate date);

    // 이전날 남은 걸음수 다음날로 이전
    @Query("SELECT s FROM StepConversion s " +
            "WHERE s.user = :user " +
            "ORDER BY s.conversionDate DESC, s.convertedAt DESC LIMIT 1")
    Optional<StepConversion> findLatestByUser(@Param("user") User user);
}
