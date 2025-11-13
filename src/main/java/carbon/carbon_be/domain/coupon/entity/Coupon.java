package carbon.carbon_be.domain.coupon.entity;

import carbon.carbon_be.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 쿠폰 이름 (예: 스타벅스 5000원 쿠폰)
    @Column(nullable = false)
    private String name;

    // 쿠폰 설명
    private String description;

    // 쿠폰 교환에 필요한 포인트
    @Column(nullable = false)
    private Integer cost;
}
