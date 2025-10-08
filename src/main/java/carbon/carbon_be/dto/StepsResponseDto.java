package carbon.carbon_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StepsResponseDto {
    private String username;
    private String recordDate;
    private int stepCount;
}
