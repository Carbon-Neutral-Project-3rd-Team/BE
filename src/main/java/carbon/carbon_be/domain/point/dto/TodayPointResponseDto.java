package carbon.carbon_be.domain.point.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class TodayPointResponseDto {
    private LocalDate date;
    private int earnedPoints;

    public static TodayPointResponseDto of(int earnedPoints) {
        return TodayPointResponseDto.builder()
                .date(LocalDate.now())
                .earnedPoints(earnedPoints)
                .build();
    }
}
