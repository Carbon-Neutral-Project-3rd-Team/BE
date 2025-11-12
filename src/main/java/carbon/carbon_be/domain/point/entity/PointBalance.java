package carbon.carbon_be.domain.point.entity;

import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "point_balance")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PointBalance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "total_points", nullable = false)
    private Integer totalPoints;

    // 생성 메서드
    public static PointBalance create(User user) {
        return PointBalance.builder()
                .user(user)
                .totalPoints(0)
                .build();
    }

    // 포인트 증가
    public void addPoints(int points) {
        this.totalPoints += points;
    }

    // 포인트 차감
    public void subtractPoints(int points) {
        this.totalPoints = Math.max(0, this.totalPoints - points);
    }
}
