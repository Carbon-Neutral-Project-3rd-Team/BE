package carbon.carbon_be.domain.coupon.repository;

import carbon.carbon_be.domain.coupon.entity.UserCoupon;
import carbon.carbon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    // 특정 유저의 쿠폰 내역 조회
    List<UserCoupon> findByUser(User user);

    // 특정 유저 + 특정 쿠폰 교환 내역 조회 (필요 시)
    List<UserCoupon> findByUserAndCouponId(User user, Long couponId);
}
