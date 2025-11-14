package carbon.carbon_be.domain.point.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointConvertResponseDto {
    private int totalSteps;         // 오늘 총 걸음수 (DailyStep)
    private int newlyConvertedSteps; // 이번 요청으로 포인트로 전환된 걸음수
    private int unconvertedSteps;   // 남은 걸음수 (50 미만)
    private int convertedPoints;    // 이번 요청에서 적립된 포인트
    private int totalPoints;        // 현재 누적 총 포인트 (Balance 기준)

    public static PointConvertResponseDto of(
            int totalSteps,
            int newlyConvertedSteps,
            int unconvertedSteps,
            int convertedPoints,
            int totalPoints
    ) {
        return PointConvertResponseDto.builder()
                .totalSteps(totalSteps)
                .newlyConvertedSteps(newlyConvertedSteps)
                .unconvertedSteps(unconvertedSteps)
                .convertedPoints(convertedPoints)
                .totalPoints(totalPoints)
                .build();
    }
}
