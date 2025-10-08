package carbon.carbon_be.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 사용자 포인트 정보를 저장하는 UserPoint 엔티티
 * User와 1:1 관계 (user_id FK)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_points")
public class UserPoint {

    /** 기본키 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User와의 1:1 관계 (FK) */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /** 현재 보유 포인트 */
    @Builder.Default
    @Column(nullable = false)
    private Long currentPoints = 0L;

    /** 총 적립된 포인트 */
    @Builder.Default
    @Column(nullable = false)
    private Long totalEarned = 0L;

    /** 총 사용(차감)된 포인트 */
    @Builder.Default
    @Column(nullable = false)
    private Long totalRedeemed = 0L;

    /** 생성 일자 */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /** 수정 일자 */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // =============================
    // 연관관계 메서드
    // =============================

    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getUserPoint() != this) {
            user.setUserPoint(this);
        }
    }

    // =============================
    // 도메인 로직
    // =============================

    /** 포인트 적립
    public void addPoints(Long amount) {
        if (amount == null || amount <= 0) return;
        this.currentPoints += amount;
        this.totalEarned += amount;
    }
     */

    /** 포인트 사용
    public boolean usePoints(Long amount) {
        if (amount == null || amount <= 0) return false;
        if (this.currentPoints < amount) return false;
        this.currentPoints -= amount;
        this.totalRedeemed += amount;
        return true;
    }
     */
}
