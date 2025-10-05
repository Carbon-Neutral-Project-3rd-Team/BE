package carbon.carbon_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/** 사용자 정보를 저장하는 User 엔티티
 **/

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_users_email", columnNames = "email"),
                @UniqueConstraint(name = "uq_users_provider", columnNames = {"provider", "provider_id"})
        }) // email번호, 각 소셜별로 고유 id값들 중복안되게 설정
@Getter
@Setter
@NoArgsConstructor
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
    private String auth_provider = "local";

    /** 소셜 제공자 내 고유 ID (providerId) */
    @Column(length = 100)
    private String provider_id;

    /** 누적 걸음 수 (기본값 0) */
    @Column(nullable = false)
    private Integer stepCnt = 0;

    /** 회원가입 시각 */
    @CreatedDate
    private LocalDateTime created_at;

    /** 최근 정보 수정 시각 */
    @LastModifiedDate
    private LocalDateTime updated_at;
}