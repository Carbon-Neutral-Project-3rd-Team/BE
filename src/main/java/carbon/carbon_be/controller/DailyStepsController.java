package carbon.carbon_be.controller;

import carbon.carbon_be.dto.StepsResponseDto;
import carbon.carbon_be.service.DailyStepsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/daily-steps")
public class DailyStepsController {
    private final DailyStepsService service;

    public DailyStepsController(DailyStepsService service) {
        this.service = service;
    }


    // 🔹 최신 걸음 수 조회 API
    @GetMapping("/{userId}")
    public StepsResponseDto getLatestSteps(@PathVariable Long userId) {
        return service.getLatestSteps(userId); // 반환 타입 변경
    }
}
