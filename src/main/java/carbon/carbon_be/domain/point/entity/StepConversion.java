package carbon.carbon_be.domain.point.entity;

import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

//포인트 전환 entity
@Entity
@Table(name = "step_conversion")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StepConversion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "conversion_date", nullable = false)
    private LocalDate conversionDate;

    // 이번에 포인트로 전환된 걸음 수
    @Column(name = "converted_steps", nullable = false)
    private int convertedSteps;

    @Column(name = "unconverted_steps", nullable = false)
    private int unconvertedSteps;

    @Column(name = "earned_points", nullable = false)
    private int earnedPoints;

    @Column(name = "converted_at", nullable = false)
    private LocalDateTime convertedAt;

    public static StepConversion of(User user, int convertedSteps, int unconvertedSteps, int earnedPoints) {
        return StepConversion.builder()
                .user(user)
                .conversionDate(LocalDate.now())
                .convertedSteps(convertedSteps)
                .unconvertedSteps(unconvertedSteps)
                .earnedPoints(earnedPoints)
                .convertedAt(LocalDateTime.now())
                .build();
    }
}