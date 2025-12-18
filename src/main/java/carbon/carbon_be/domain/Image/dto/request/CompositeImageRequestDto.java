package carbon.carbon_be.domain.Image.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompositeImageRequestDto {
    @Valid @NotNull private ItemCondition hat;
    @Valid @NotNull private ItemCondition clothes;
    @Valid @NotNull private ItemCondition shoes;

    @Data
    public static class ItemCondition {
        @NotNull private String type;
        @NotNull private String color;
    }
}

