package carbon.carbon_be;

import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.dailystep.repository.DailyStepRepository;
import carbon.carbon_be.domain.point.dto.PointConvertResponseDto;
import carbon.carbon_be.domain.point.entity.PointBalance;
import carbon.carbon_be.domain.point.entity.PointHistory;
import carbon.carbon_be.domain.point.repository.PointBalanceRepository;
import carbon.carbon_be.domain.point.repository.PointHistoryRepository;
import carbon.carbon_be.domain.point.repository.StepConversionRepository;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.repository.UserRepository;
import carbon.carbon_be.domain.point.service.PointConvertService;
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
class PointConvertServiceTest {

    @Autowired private PointConvertService pointConvertService;
    @Autowired private UserRepository userRepository;
    @Autowired private DailyStepRepository dailyStepRepository;
    @Autowired private PointHistoryRepository pointHistoryRepository;
    @Autowired private StepConversionRepository stepConversionRepository;
    @Autowired private PointBalanceRepository pointBalanceRepository;

    @DisplayName("✅ 오늘 걸음수 수동 포인트 전환 테스트")
    @Test
    void convertStepsTodayTest() {
        // given
        User user = userRepository.save(
                User.builder()
                        .email("today@inha.ac.kr")
                        .password("1234")
                        .username("테스트유저")
                        .build()
        );

        DailyStep dailyStep = dailyStepRepository.save(
                DailyStep.builder()
                        .user(user)
                        .recordDate(LocalDate.now())
                        .stepCount(5250)  // 오늘 5250걸음
                        .build()
        );

        // when
        PointConvertResponseDto response = pointConvertService.convertStepsToPointsManual(user);

        // then
        assertThat(response.getConvertedPoints()).isEqualTo(5250 / 50); // 105포인트
        assertThat(response.getNewlyConvertedSteps()).isEqualTo(5250 / 50 * 50); // 5250보 전환됨
        assertThat(response.getUnconvertedSteps()).isEqualTo(5250 % 50); // 잔여 0
        assertThat(response.getTotalSteps()).isEqualTo(5250);

        // ✅ DB 반영 확인
        List<PointHistory> histories = pointHistoryRepository.findByUserOrderByRecordDateDesc(user);
        assertThat(histories).isNotEmpty();
        assertThat(histories.get(0).getEarnedPoints()).isEqualTo(105);

        PointBalance balance = pointBalanceRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("밸런스가 생성되지 않았음"));
        assertThat(balance.getTotalPoints()).isEqualTo(105);

        System.out.println("✅ 테스트 통과: 오늘 걸음수 포인트 전환 성공");
        System.out.println("총 걸음수: " + response.getTotalSteps());
        System.out.println("이번 전환 걸음수: " + response.getNewlyConvertedSteps());
        System.out.println("적립 포인트: " + response.getConvertedPoints());
        System.out.println("누적 포인트: " + response.getTotalPoints());
    }
}
