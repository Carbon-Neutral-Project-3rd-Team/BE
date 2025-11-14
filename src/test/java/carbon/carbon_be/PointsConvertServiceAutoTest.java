package carbon.carbon_be;

import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.dailystep.repository.DailyStepRepository;
import carbon.carbon_be.domain.point.entity.PointBalance;
import carbon.carbon_be.domain.point.entity.PointHistory;
import carbon.carbon_be.domain.point.repository.PointBalanceRepository;
import carbon.carbon_be.domain.point.repository.PointHistoryRepository;
import carbon.carbon_be.domain.point.service.PointConvertService;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointConvertServiceAutoTest {

    @Autowired private PointConvertService pointConvertService;
    @Autowired private UserRepository userRepository;
    @Autowired private DailyStepRepository dailyStepRepository;
    @Autowired private PointHistoryRepository pointHistoryRepository;
    @Autowired private PointBalanceRepository pointBalanceRepository;

    @DisplayName("✅ 자정 자동 포인트 전환 로직 테스트")
    @Test
    void testAutoConvertStepsToPoints() {
        // given
        User user = userRepository.save(
                User.builder()
                        .email("auto@inha.ac.kr")
                        .password("1234")
                        .username("자동유저")
                        .build()
        );

        LocalDate yesterday = LocalDate.now().minusDays(1);

        // 어제 걸음 수 저장 (하루 최대치 테스트: 10,000보)
        dailyStepRepository.save(
                DailyStep.builder()
                        .user(user)
                        .recordDate(yesterday)
                        .stepCount(10_000)
                        .build()
        );

        // when
        pointConvertService.convertStepsToPointsAuto();

        // then
        // ✅ 1) 포인트 이력 생성 확인
        List<PointHistory> histories = pointHistoryRepository.findByUserOrderByRecordDateDesc(user);
        assertThat(histories).hasSize(1);
        PointHistory history = histories.get(0);
        assertThat(history.getRecordDate()).isEqualTo(yesterday);
        assertThat(history.getEarnedPoints()).isEqualTo(200); // 50보당 1포인트 → 200포인트

        // ✅ 2) 누적 포인트 확인
        PointBalance balance = pointBalanceRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("밸런스가 생성되지 않았음"));
        assertThat(balance.getTotalPoints()).isEqualTo(200);

        System.out.println("✅ 자정 자동 포인트 전환 테스트 통과");
        System.out.println("record_date = " + history.getRecordDate());
        System.out.println("earned_points = " + history.getEarnedPoints());
        System.out.println("total_balance = " + balance.getTotalPoints());
    }
}