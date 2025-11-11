package carbon.carbon_be.domain.dailystep.controller;

import carbon.carbon_be.domain.dailystep.dto.response.StepStatsResponseDto;
import carbon.carbon_be.domain.dailystep.service.StepStatsService;
import carbon.carbon_be.domain.totalstep.dto.response.TotalStepResponseDto;
import carbon.carbon_be.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StepStatsCountController {

   private final StepStatsService stepStatsService;

   @GetMapping("/weekly")
    public StepStatsResponseDto getWeeklyStats(@AuthenticationPrincipal CustomUserDetails user) {
        return stepStatsService.getWeeklyStats(user.getId());
    }

    @GetMapping("/monthly")
    public StepStatsResponseDto getMonthlyStats(@AuthenticationPrincipal CustomUserDetails user) {
        return stepStatsService.getMonthlyStats(user.getId());
    }

    @GetMapping("/total")
    public TotalStepResponseDto getTotalSteps(@AuthenticationPrincipal CustomUserDetails user) {
        return stepStatsService.getTotalSteps(user.getId());
    }
}
