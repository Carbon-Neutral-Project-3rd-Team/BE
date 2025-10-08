package carbon.carbon_be.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "daily_steps",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_daily_steps_user_date", columnNames = {"user_id", "record_date"})
        })
public class DailyStep {

    /** 기본키 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 걸음 수 기록 날짜 */
    @Column(nullable = false)
    private LocalDate recordDate;

    /** 하루 걸음 수 */
    @Column(nullable = false)
    private int stepCount;

    /** 1:N 데이터 관계(날짜별로 있으니) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // =============================
    // 연관관계 메서드
    // =============================

    /** 연관관계 설정 (User ↔ DailyStep) */
    public void setUser(User user) {
        this.user = user;
    }

    // =============================
    // 도메인 로직
    // =============================

    /**
    public void updateStepCount(int steps) {
        if (steps < 0) throw new IllegalArgumentException("걸음 수는 음수가 될 수 없습니다.");
        this.stepCount = steps;
    }
     */

    /**
    public void addSteps(int steps) {
        if (steps <= 0) return;
        this.stepCount += steps;
    }
     */
}
