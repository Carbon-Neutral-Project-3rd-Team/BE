package carbon.carbon_be;

import carbon.carbon_be.domain.point.entity.StepConversion;
import carbon.carbon_be.domain.point.repository.StepConversionRepository;
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
class StepConversionTest {

    @Autowired
    private StepConversionRepository stepConversionRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("✅ StepConversion.of() 생성 및 저장 테스트")
    @Test
    void createStepConversion() {
        // given
        User user = userRepository.save(
                User.builder()
                        .email("convert@inha.ac.kr")
                        .password("1234")
                        .username("전환유저")
                        .build()
        );

        // when
        StepConversion conversion = StepConversion.of(user, 500, 10, 10);
        stepConversionRepository.save(conversion);

        // then
        List<StepConversion> results = stepConversionRepository.findByUserAndConversionDate(user, LocalDate.now());
        assertThat(results).hasSize(1);

        StepConversion saved = results.get(0);
        assertThat(saved.getConvertedSteps()).isEqualTo(500);
        assertThat(saved.getEarnedPoints()).isEqualTo(10);
        assertThat(saved.getUser().getId()).isEqualTo(user.getId());

        System.out.println("✅ StepConversion 저장 성공");
        System.out.println("convertedSteps = " + saved.getConvertedSteps());
        System.out.println("earnedPoints = " + saved.getEarnedPoints());
        System.out.println("conversionDate = " + saved.getConversionDate());
    }

    @DisplayName("✅ 하루 동안 여러 번 전환 시 합계(sumConvertedStepsByUserAndDate) 계산 테스트")
    @Test
    void sumConvertedStepsTest() {
        // given
        User user = userRepository.save(
                User.builder()
                        .email("sum@inha.ac.kr")
                        .password("1234")
                        .username("합계유저")
                        .build()
        );

        stepConversionRepository.save(StepConversion.of(user, 250, 0, 5));
        stepConversionRepository.save(StepConversion.of(user, 300, 0, 6));
        stepConversionRepository.save(StepConversion.of(user, 400, 0, 8));

        // when
        int totalConverted = stepConversionRepository.sumConvertedStepsByUserAndDate(user, LocalDate.now());

        // then
        assertThat(totalConverted).isEqualTo(950); // 250+300+400
        System.out.println("✅ 오늘 전환된 총 걸음수 합계: " + totalConverted);
    }
}
