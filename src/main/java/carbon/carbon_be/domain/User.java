package carbon.carbon_be.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** 사용자 정보를 저장하는 User 엔티티 **/

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_users_email", columnNames = "email"),
                @UniqueConstraint(name = "uq_users_provider", columnNames = {"auth_provider", "provider_id"})
        })
public class User {
    /** 기본키 (Primary Key) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 이메일, 일부 소셜은 이메일 비공개일 수 있으므로 nullable 처리 가능 */
    @Column(length = 255, unique = true, nullable = true)
    private String email;

    /** 비밀번호 (소셜 로그인 시 null 가능) */
    @Column(length = 255, nullable = true)
    private String password;

    /** 사용자 이름, 닉네임 */
    @Column(nullable = false, length = 50)
    private String username;

    /** 권한 */
    @Column(nullable = false, length = 20)
    private String role = "USER";

    /** 로그인 제공자 (local = 회원가입, 로그인, kakao, google, naver 등) */
    @Column(nullable = false, length = 20)
    private String authProvider = "local";

    /** 소셜 제공자 내 고유 ID (providerId) */
    @Column(length = 100)
    private String providerId;

    /** 누적 걸음 수 (기본값 0) */
    @Column(nullable = false)
    private Integer stepCnt = 0;

    /** 회원가입 시각 */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /** 최근 정보 수정 시각 */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /** UserPoint와의 1:1 관계 */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserPoint userPoint;


    // =============================
    // 연관관계 메서드
    // =============================

    public void setUserPoint(UserPoint userPoint) {
        this.userPoint = userPoint;
        if (userPoint != null && userPoint.getUser() != this) {
            userPoint.setUser(this);
        }
    }


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DailyStep> dailySteps = new ArrayList<>();


    public void addDailyStep(DailyStep step) {
        this.dailySteps.add(step);
        if (step.getUser() != this && step != null) {
            step.setUser(this);
        }
    }
}