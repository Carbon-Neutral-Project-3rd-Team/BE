package carbon.carbon_be.domain.point.dto;

import carbon.carbon_be.domain.point.entity.PointHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PointHistoryResponseDto {

    private LocalDate recordDate;   // 날짜
    private Integer earnedPoints;   // 적립된 포인트
    private String source;          // 포인트 발생 원인

    public static PointHistoryResponseDto fromEntity(PointHistory entity) {
        return PointHistoryResponseDto.builder()
                .recordDate(entity.getRecordDate())
                .earnedPoints(entity.getEarnedPoints())
                .source(entity.getSource())
                .build();
    }
}
