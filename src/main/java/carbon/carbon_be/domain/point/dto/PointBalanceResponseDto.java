package carbon.carbon_be.domain.point.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointBalanceResponseDto {
    private int totalPoints;   // 총 누적 포인트

    public static PointBalanceResponseDto of(int totalPoints) {
        return PointBalanceResponseDto.builder()
                .totalPoints(totalPoints)
                .build();
    }
}
