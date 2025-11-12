package carbon.carbon_be;

import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.point.entity.PointHistory;
import carbon.carbon_be.domain.point.repository.PointHistoryRepository;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointHistoryTest {

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("✅ DailyStep 기반 PointHistory 생성 및 저장 테스트")
    @Test
    void createPointHistoryFromDailyStep() {
        // given
        User user = userRepository.save(
                User.builder()
                        .email("test@inha.ac.kr")
                        .password("1234")
                        .username("테스트유저")
                        .build()
        );

        DailyStep dailyStep = DailyStep.builder()
                .user(user)
                .recordDate(LocalDate.now())
                .stepCount(5250) // 5250보
                .build();

        // when
        PointHistory history = PointHistory.fromDailyStep(user, dailyStep.getRecordDate(), dailyStep.getStepCount());
        pointHistoryRepository.save(history);

        // then
        PointHistory saved = pointHistoryRepository.findByUserAndRecordDate(user, LocalDate.now()).orElseThrow();

        assertThat(saved.getUser().getId()).isEqualTo(user.getId());
        assertThat(saved.getEarnedPoints()).isEqualTo(5250 / 50); // 50보당 1P → 105P
        assertThat(saved.getSource()).isEqualTo("DAILY_STEP");
        assertThat(saved.getRecordDate()).isEqualTo(LocalDate.now());

        System.out.println("✅ 포인트 이력 저장 성공:");
        System.out.println("user_id = " + saved.getUser().getId());
        System.out.println("record_date = " + saved.getRecordDate());
        System.out.println("earned_points = " + saved.getEarnedPoints());
        System.out.println("source = " + saved.getSource());
    }
}