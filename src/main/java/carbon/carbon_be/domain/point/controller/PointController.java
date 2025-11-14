package carbon.carbon_be.domain.point.controller;

import carbon.carbon_be.domain.point.dto.PointBalanceResponseDto;
import carbon.carbon_be.domain.point.dto.PointConvertResponseDto;
import carbon.carbon_be.domain.point.dto.PointHistoryResponseDto;
import carbon.carbon_be.domain.point.dto.TodayPointResponseDto;
import carbon.carbon_be.domain.point.service.PointConvertService;
import carbon.carbon_be.domain.point.service.PointQueryService;
import carbon.carbon_be.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {

    private final PointQueryService pointQueryService;
    private final PointConvertService pointConvertService;

    //사용자 포인트 적립 이력 전체 조회 (최신순)
    //각 일별 조회
    @GetMapping("/history")
    public List<PointHistoryResponseDto> getMyPointHistory(
            @AuthenticationPrincipal CustomUserDetails user) {

        // 사용자 기준 포인트 이력 조회
        return pointQueryService.getUserPointHistory(user.getUser());
    }

    //오늘 적립된 포인트 조회
    @GetMapping("/today")
    public TodayPointResponseDto getTodayPoints(@AuthenticationPrincipal CustomUserDetails user) {
        return pointQueryService.getTodayPoints(user.getUser());
    }

        //누적 포인트 조회
    @GetMapping("/balance")
    public PointBalanceResponseDto getTotalPoints(@AuthenticationPrincipal CustomUserDetails user) {
        return pointQueryService.getTotalPoints(user.getUser());
    }

    //포인트 전환
    @PostMapping("/convert")
    public PointConvertResponseDto convertSteps(@AuthenticationPrincipal CustomUserDetails user) {
        return pointConvertService.convertStepsToPointsManual(user.getUser());
    }

}
