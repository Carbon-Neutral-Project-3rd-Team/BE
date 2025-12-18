package carbon.carbon_be.domain.Image.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompositeImageResponseDto {
    private String imageUrl;
}

