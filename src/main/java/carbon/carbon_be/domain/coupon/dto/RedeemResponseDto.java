package carbon.carbon_be.domain.coupon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RedeemResponseDto {

    private String couponName;      // 교환한 쿠폰 이름
    private Integer remainingPoint; // 남은 포인트
    private String status;          // 쿠폰 상태 (UNUSED)
    private String issuedAt;        // 발급 시간
}
