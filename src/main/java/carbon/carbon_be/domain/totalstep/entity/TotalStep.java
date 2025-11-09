package carbon.carbon_be.domain.totalstep.entity;

import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "total_steps")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalStep extends BaseTimeEntity {

   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
   private User user;

   @Column(name = "total_step_count", nullable = false)
    private Long totalStepCount;

   public void addSteps(long delta) {
        if (delta <= 0) return;
        this.totalStepCount += delta;
   }
}
