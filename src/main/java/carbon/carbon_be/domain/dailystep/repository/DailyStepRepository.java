package carbon.carbon_be.domain.dailystep.repository;

import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyStepRepository extends JpaRepository<DailyStep, Long> {
   Optional<DailyStep> findByUserAndRecordDate(User user, LocalDate date);

   //특정 날짜의 모든 유저 걸음 데이터 조회 (자동 포인트 적립용)
    List<DailyStep> findByRecordDate(LocalDate recordDate);

    // 특정 기간 동안의 일별 걸음수 조회
    @Query("SELECT ds FROM DailyStep ds " +
            "WHERE ds.user = :user " +
            "AND ds.recordDate BETWEEN :startDate AND :endDate " +
            "ORDER BY ds.recordDate ASC")
    List<DailyStep> findByUserAndDateRange(@Param("user") User user,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);
}
