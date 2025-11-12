package carbon.carbon_be.domain.point.repository;

import carbon.carbon_be.domain.point.entity.PointHistory;
import carbon.carbon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    // 특정 유저 기준 이미 포인터 적립이 된 날짜인지 확인 (중복 적립 방지용)
    boolean existsByUserAndRecordDate(User user, LocalDate recordDate);

    // 특정 유저의 전체 포인트 적립 내역 조회 (최신순)
    List<PointHistory> findByUserOrderByRecordDateDesc(User user);

    // 특정 기간 동안의 포인트 적립 내역 조회
    @Query("SELECT ph FROM PointHistory ph " +
            "WHERE ph.user = :user " +
            "AND ph.recordDate BETWEEN :startDate AND :endDate " +
            "ORDER BY ph.recordDate ASC")
    List<PointHistory> findByUserAndDateRange(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
