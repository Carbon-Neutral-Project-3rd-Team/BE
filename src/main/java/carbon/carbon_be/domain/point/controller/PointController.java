package carbon.carbon_be.domain.point.controller;

import carbon.carbon_be.domain.point.dto.PointHistoryResponseDto;
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


//     사용자 포인트 적립 이력 전체 조회 (최신순)
//     GET /api/points/history
//     응답: [
//            { "recordDate": "2025-11-11",
//              "earnedPoints": 200,
//              "source": "DAILY_STEP"
//            },
//            { "recordDate": "2025-11-10",
//              "earnedPoints": 174,
//              "source": "DAILY_STEP"
//            }
//          ]

    @GetMapping("/history")
    public List<PointHistoryResponseDto> getMyPointHistory(
            @AuthenticationPrincipal CustomUserDetails user) {

        // 사용자 기준 포인트 이력 조회
        return pointQueryService.getUserPointHistory(user.getUser());
    }
}
