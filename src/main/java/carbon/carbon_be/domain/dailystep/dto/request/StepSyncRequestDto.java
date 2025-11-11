package carbon.carbon_be.domain.dailystep.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepSyncRequestDto {
    private int stepCount; // 오늘 걸음수
    private LocalDate recordDate;
}