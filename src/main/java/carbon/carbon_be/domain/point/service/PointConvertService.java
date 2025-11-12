package carbon.carbon_be.domain.point.service;

import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.dailystep.repository.DailyStepRepository;
import carbon.carbon_be.domain.point.dto.PointConvertResponseDto;
import carbon.carbon_be.domain.point.entity.PointBalance;
import carbon.carbon_be.domain.point.entity.PointHistory;
import carbon.carbon_be.domain.point.entity.StepConversion;
import carbon.carbon_be.domain.point.repository.PointBalanceRepository;
import carbon.carbon_be.domain.point.repository.PointHistoryRepository;
import carbon.carbon_be.domain.point.repository.StepConversionRepository;
import carbon.carbon_be.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointConvertService {

    private final DailyStepRepository dailyStepRepository;
    private final StepConversionRepository stepConversionRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointBalanceRepository pointBalanceRepository;


    //자동 실행 (자정)
    @Transactional
    public void convertStepsToPointsAuto() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<DailyStep> steps = dailyStepRepository.findByRecordDate(yesterday);

        for (DailyStep ds : steps) {
            // 중복 적립 방지
            if (pointHistoryRepository.existsByUserAndRecordDate(ds.getUser(), yesterday))
                continue;

            // 어제 남은 잔여 걸음 이월 반영
            int yesterdayRemainder = stepConversionRepository.findLatestByUser(ds.getUser())
                    .map(StepConversion::getUnconvertedSteps)
                    .orElse(0);
            int totalSteps = ds.getStepCount() + yesterdayRemainder;
            int validSteps = Math.min(ds.getStepCount(), 10_000);
            int points = validSteps / 50;
            int remainder = totalSteps % 50;

            // 이월 저장
            stepConversionRepository.save(StepConversion.of(ds.getUser(), validSteps - remainder, remainder, points));

            // PointHistory 생성
            PointHistory history = PointHistory.builder()
                    .user(ds.getUser())
                    .recordDate(yesterday)
                    .earnedPoints(points)
                    .source("AUTO_CONVERT")
                    .build();
            pointHistoryRepository.save(history);

            // PointBalance 업데이트 (없으면 생성)
            PointBalance balance = pointBalanceRepository.findByUser(ds.getUser())
                    .orElseGet(() -> pointBalanceRepository.save(
                            PointBalance.builder()
                                    .user(ds.getUser())
                                    .totalPoints(0)
                                    .build()
                    ));
            balance.addPoints(points);
        }
    }

    @Transactional
    public PointConvertResponseDto convertStepsToPointsManual(User user) {
        LocalDate today = LocalDate.now();

        // 오늘의 걸음수
        DailyStep dailyStep = dailyStepRepository.findByUserAndRecordDate(user, today)
                .orElseThrow(() -> new IllegalStateException("오늘 걸음 데이터가 없습니다."));

        // 오늘 걸음수
        int todaySteps = dailyStep.getStepCount();

        // 어제 잔여 걸음수
        int yesterdayRemainder = stepConversionRepository.findLatestByUser(user)
                .map(StepConversion::getUnconvertedSteps)
                .orElse(0);

        int totalSteps = todaySteps + yesterdayRemainder;

        // 지금까지 전환된 걸음수 합계
        int convertedSteps = stepConversionRepository.sumConvertedStepsByUserAndDate(user, today);
        int remainingSteps = totalSteps - convertedSteps;

        if (remainingSteps < 50) {
            return PointConvertResponseDto.of(
                    totalSteps,           // 오늘 총 걸음수
                    0,                    // 이번 요청으로 전환된 걸음수
                    remainingSteps,       // 아직 포인트로 바꾸지 못한 걸음수
                    0,                    // 이번 요청에서 새로 적립된 포인트 없음
                    getTotalBalance(user) // 누적 총 포인트
            );
        }

        // 새로 전환할 포인트 계산
        int convertibleSteps = Math.min(remainingSteps, 10_000 - convertedSteps);
        int earnedPoints = convertibleSteps / 50;
        int usedSteps = earnedPoints * 50;
        int unconvertedSteps = remainingSteps - usedSteps;

        // StepConversion 로그 저장
        StepConversion conversion = StepConversion.of(user, usedSteps, unconvertedSteps, earnedPoints);
        stepConversionRepository.save(conversion);

        // PointHistory & Balance 갱신
        PointHistory history = PointHistory.builder()
                .user(user)
                .recordDate(today)
                .earnedPoints(earnedPoints)
                .source("MANUAL_CONVERT")
                .build();
        pointHistoryRepository.save(history);

        PointBalance balance = pointBalanceRepository.findByUser(user)
                .orElseGet(() -> pointBalanceRepository.save(
                        PointBalance.builder().user(user).totalPoints(0).build()
                ));
        balance.addPoints(earnedPoints);

        return PointConvertResponseDto.of(
                totalSteps,              // 오늘 총 걸음수
                usedSteps,               // 이번 요청으로 바뀐 걸음수
                unconvertedSteps,        // 아직 남은 걸음수
                earnedPoints,            // 이번에 새로 적립된 포인트
                balance.getTotalPoints() // 누적 총 포인트
        );
    }

    private int getTotalBalance(User user) {
        return pointBalanceRepository.findByUser(user)
                .map(PointBalance::getTotalPoints)
                .orElse(0);
    }
}
