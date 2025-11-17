package carbon.carbon_be.domain.coupon.repository;

import carbon.carbon_be.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
