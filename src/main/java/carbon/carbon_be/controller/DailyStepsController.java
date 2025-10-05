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

    @GetMapping("/{userId}")
    public DailySteps getDailySteps(@PathVariable Long userId,
                                    @RequestParam LocalDate date) {
        return service.getSteps(userId, date);
    }
}
