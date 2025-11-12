package carbon.carbon_be.domain.point.repository;

import carbon.carbon_be.domain.point.entity.StepConversion;
import carbon.carbon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StepConversionRepository extends JpaRepository<StepConversion, Long> {

    List<StepConversion> findByUserAndConversionDate(User user, LocalDate date);

    @Query("SELECT COALESCE(SUM(s.convertedSteps), 0) FROM StepConversion s " +
            "WHERE s.user = :user AND s.conversionDate = :date")
    int sumConvertedStepsByUserAndDate(User user, LocalDate date);
}
