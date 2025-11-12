package carbon.carbon_be.domain.point.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class PointTodayResponseDto {
    private LocalDate date;        // 오늘 날짜
    private int earnedPoints;      // 적립된 포인트 (없으면 0)

    public static PointTodayResponseDto of(LocalDate date, int points) {
        return PointTodayResponseDto.builder()
                .date(date)
                .earnedPoints(points)
                .build();
    }
}
