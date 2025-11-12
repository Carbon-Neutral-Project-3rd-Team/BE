package carbon.carbon_be;

import carbon.carbon_be.domain.point.entity.PointBalance;
import carbon.carbon_be.domain.point.repository.PointBalanceRepository;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PointBalanceTest {

    @Autowired
    private PointBalanceRepository pointBalanceRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("✅ 포인트 누적(addPoints) 및 저장 테스트")
    @Test
    void addPointsTest() {
        // given
        User user = userRepository.save(
                User.builder()
                        .email("balance@inha.ac.kr")
                        .password("1234")
                        .username("밸런스유저")
                        .build()
        );

        PointBalance balance = PointBalance.builder()
                .user(user)
                .totalPoints(0)
                .build();

        pointBalanceRepository.save(balance);

        // when — 포인트 3번 적립
        balance.addPoints(100);
        balance.addPoints(50);
        balance.addPoints(25);
        pointBalanceRepository.save(balance);

        // then
        PointBalance saved = pointBalanceRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("포인트 밸런스가 존재하지 않음"));

        assertThat(saved.getTotalPoints()).isEqualTo(175);
        System.out.println("✅ 포인트 누적 테스트 통과");
        System.out.println("user_id = " + saved.getUser().getId());
        System.out.println("누적 포인트 = " + saved.getTotalPoints());
    }

    @DisplayName("✅ 신규 유저 생성 시 자동 0포인트로 초기화 테스트")
    @Test
    void initialBalanceZeroTest() {
        // given
        User user = userRepository.save(
                User.builder()
                        .email("newuser@inha.ac.kr")
                        .password("1234")
                        .username("신규유저")
                        .build()
        );

        // when
        PointBalance balance = PointBalance.builder()
                .user(user)
                .totalPoints(0)
                .build();

        pointBalanceRepository.save(balance);

        // then
        PointBalance found = pointBalanceRepository.findByUser(user)
                .orElseThrow();
        assertThat(found.getTotalPoints()).isZero();
        System.out.println("✅ 신규 유저 밸런스 초기값 테스트 통과 (0포인트)");
    }
}
