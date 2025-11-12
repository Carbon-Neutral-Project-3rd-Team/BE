package carbon.carbon_be.domain.point.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointScheduler {

    private final PointService pointService;

//     매일 00:00 (한국시간) 자동 포인트 적립 실행
//     cron 형식: 초 분 시 일 월 요일

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void runDailyPointConversion() {
        pointService.convertStepsToPoints();
    }
}
