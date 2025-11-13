package carbon.carbon_be.domain.coupon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponListResponseDto {

    private Long couponId;
    private String name;
    private String description;
    private Integer cost;
}
