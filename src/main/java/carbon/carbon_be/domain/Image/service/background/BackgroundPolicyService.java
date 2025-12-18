package carbon.carbon_be.domain.Image.service.background;

import org.springframework.stereotype.Service;

@Service
public class BackgroundPolicyService {

    private static final long PARK_THRESHOLD = 5_000L * 7;    // 35,000
    private static final long STUDIO_THRESHOLD = 10_000L * 7; // 70,000

    public String decideByWeeklySum(long weeklySum) {
        if (weeklySum >= STUDIO_THRESHOLD) return "studio";
        if (weeklySum >= PARK_THRESHOLD) return "park";
        return "room";
    }
}
