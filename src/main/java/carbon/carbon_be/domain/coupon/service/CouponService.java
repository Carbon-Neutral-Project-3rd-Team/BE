package carbon.carbon_be.domain.coupon.service;

import carbon.carbon_be.domain.coupon.dto.CouponListResponseDto;
import carbon.carbon_be.domain.coupon.dto.RedeemResponseDto;
import carbon.carbon_be.domain.coupon.entity.Coupon;
import carbon.carbon_be.domain.coupon.entity.UserCoupon;
import carbon.carbon_be.domain.coupon.repository.CouponRepository;
import carbon.carbon_be.domain.coupon.repository.UserCouponRepository;
import carbon.carbon_be.domain.point.entity.PointBalance;
import carbon.carbon_be.domain.point.repository.PointBalanceRepository;
import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final PointBalanceRepository pointBalanceRepository;

    @Transactional
    public RedeemResponseDto redeemCoupon(Long userId, Long couponId) {

        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 교환하려는 쿠폰 조회
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));

        // 유저의 포인트 잔액 조회
        PointBalance pointBalance = pointBalanceRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("포인트 정보가 없습니다."));

        // 포인트 부족 체크
        if (pointBalance.getTotalPoints() < coupon.getCost()) {
            throw new IllegalStateException("포인트가 부족합니다.");
        }

        // 포인트 차감
        pointBalance.subtractPoints(coupon.getCost());

        // 유저 쿠폰 생성 및 저장
        UserCoupon userCoupon = UserCoupon.create(user, coupon);
        userCouponRepository.save(userCoupon);

        // DTO 반환
        return RedeemResponseDto.builder()
                .couponName(coupon.getName())
                .remainingPoint(pointBalance.getTotalPoints())
                .status(userCoupon.getStatus().name())
                .issuedAt(userCoupon.getCreatedAt().toString())
                .build();
    }

    // 쿠폰 목록 조회(GET)
    public List<CouponListResponseDto> getCouponList() {
        return couponRepository.findAll().stream()
                .map(coupon -> CouponListResponseDto.builder()
                        .couponId(coupon.getId())
                        .name(coupon.getName())
                        .description(coupon.getDescription())
                        .cost(coupon.getCost())
                        .build()
                )
                .toList();
    }
}
