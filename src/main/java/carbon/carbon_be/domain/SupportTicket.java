package carbon.carbon_be.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "support_ticket")
public class SupportTicket {

    /** 기본키 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 작성자 (User와 N:1 관계) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** 작성자 이름 (User 삭제 시에도 유지하기 위해 캐싱) */
    @Column(nullable = false, length = 50)
    private String userName;

    /** 문의 카테고리 */
    @Column(nullable = false, length = 50)
    private String category;

    /** 문의 제목 */
    @Column(nullable = false, length = 150)
    private String subject;

    /** 문의 내용 */
    @Column(nullable = false, columnDefinition = "TEXT") // 내용이 길 수 있기에 TEXT로 선언
    private String content;

    /** 공개 여부 */
    @Column(nullable = false)
    @Builder.Default
    private Boolean isPublic = false;

    /** 접수 상태 */
    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "OPEN";

    /** 관리자 답변 내용 */
    @Column(columnDefinition = "TEXT")
    private String adminResponse;

    /** 사용자가 마지막으로 수정한 시각 */
    @Column(name = "last_edited_date")
    private LocalDateTime lastEditedDate;

    /** 관리자 답변 작성 시각 */
    @Column(name = "admin_response_date")
    private LocalDateTime adminResponseDate;

    /** 응답한 관리자 ID */
    @Column
    private Long respondedBy;

    /** 생성 시각 */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /** 최근 수정 시각 */
    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;

    // =============================
    // 연관관계 메서드
    // =============================

    public void setUser(User user) {
        this.user = user;
        if (user != null && this.userName == null) {
            this.userName = user.getUsername();
        }
    }


    // =============================
    // 도메인 로직
    // =============================

    /** 티켓 상태 변경
    public void updateStatus(String newStatus) {
        if (newStatus == null || newStatus.isBlank()) return;
        this.status = newStatus;
    }
     */

    /** 관리자 답변 추가
    public void respond(String adminResponse, Long adminId) {
        this.adminResponse = adminResponse;
        this.adminResponseDate = LocalDateTime.now();
        this.status = "ANSWERED";
        this.respondedBy = adminId;
    }
     */

    /** 사용자 수정 시각 갱신
    public void updateContent(String newSubject, String newContent) {
        this.subject = newSubject;
        this.content = newContent;
        this.lastEditedDate = LocalDateTime.now();
    }
     */
}
