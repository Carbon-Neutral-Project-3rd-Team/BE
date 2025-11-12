package carbon.carbon_be.domain.point.service;

import carbon.carbon_be.domain.dailystep.entity.DailyStep;
import carbon.carbon_be.domain.dailystep.repository.DailyStepRepository;
import carbon.carbon_be.domain.point.entity.PointHistory;
import carbon.carbon_be.domain.point.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final DailyStepRepository dailyStepRepository;
    private final PointHistoryRepository pointHistoryRepository;

//     하루 걸음 수 기반 포인트 적립
//     - 기준: 50걸음당 1포인트 (최대 200포인트)
//     - 대상: 어제 날짜의 DailyStep 데이터

    @Transactional
    public void convertStepsToPoints() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // 어제의 모든 DailyStep 조회
        List<DailyStep> steps = dailyStepRepository.findByRecordDate(yesterday);

        // 각 사용자별 포인트 적립
        for (DailyStep ds : steps) {
            // 이미 어제 포인트 이력이 있으면 skip
            if (pointHistoryRepository.existsByUserAndRecordDate(ds.getUser(), yesterday))
                continue;

            // 새 포인트 이력 생성
            PointHistory history = PointHistory.fromDailyStep(
                    ds.getUser(),
                    yesterday,
                    ds.getStepCount()
            );

            // 저장
            pointHistoryRepository.save(history);
        }
    }
}
