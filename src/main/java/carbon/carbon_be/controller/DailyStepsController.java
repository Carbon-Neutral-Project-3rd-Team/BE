package carbon.carbon_be.controller;

import carbon.carbon_be.entity.DailySteps;
import carbon.carbon_be.service.DailyStepsService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
@RestController
@RequestMapping("/api/daily-steps")
public class DailyStepsController {
    private final DailyStepsService service;

    public DailyStepsController(DailyStepsService service) {
        this.service = service;
    }

    // 🔹 최신 걸음 수 조회 API
    @GetMapping("/{userId}")
    public DailySteps getLatestSteps(@PathVariable Long userId) {
        return service.getLatestSteps(userId);
    }
}
