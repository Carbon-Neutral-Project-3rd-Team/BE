package carbon.carbon_be.domain.point.entity;


import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "point_history",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "record_date"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PointHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 포인트 적립 대상 유저

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;  // 포인트 적립 기준 날짜

    @Column(name = "earned_points", nullable = false)
    private Integer earnedPoints;  // 해당 날짜에 적립된 포인트 수

    @Column(name = "source", nullable = false)
    private String source; // 포인트 발생 원인 -> 자정 적립 이외에 기능고려해서 추가

    //  하루 걸음 수 기반으로 PointHistory 생성
    public static PointHistory fromDailyStep(User user, LocalDate date, int stepCount) {
        int validSteps = Math.min(stepCount, 10_000); // 1만 보 제한
        int points = validSteps / 50;                // 50걸음 = 1포인트
        return PointHistory.builder()
                .user(user)
                .recordDate(date)
                .earnedPoints(points)
                .source("DAILY_STEP")
                .build();
    }
}
