package carbon.carbon_be.domain.coupon.controller;

import carbon.carbon_be.domain.coupon.dto.CouponListResponseDto;
import carbon.carbon_be.domain.coupon.dto.RedeemRequestDto;
import carbon.carbon_be.domain.coupon.dto.RedeemResponseDto;
import carbon.carbon_be.domain.coupon.entity.Coupon;
import carbon.carbon_be.domain.coupon.service.CouponService;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/redeem")
    public RedeemResponseDto redeemCoupon(
            @AuthenticationPrincipal User user,
            @RequestBody RedeemRequestDto requestDto
    ) {
        Long userId = user.getId();
        return couponService.redeemCoupon(userId, requestDto.getCouponId());
    }

    // ✔ 2) 쿠폰 목록 조회 API
    @GetMapping("/list")
    public List<CouponListResponseDto> getCouponList() {
        return couponService.getCouponList();
    }
}
