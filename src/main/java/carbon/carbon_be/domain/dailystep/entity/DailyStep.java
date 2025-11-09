package carbon.carbon_be.domain.dailystep.entity;

import carbon.carbon_be.domain.user.entity.User;
import carbon.carbon_be.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(
     name = "daily_steps",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "record_date"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DailyStep extends BaseTimeEntity {

   @Id @GeneratedValue
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY) // N+1 문제 방지
   @JoinColumn(name = "user_id", nullable = false)
   private User user;

   @Column(name="step_count")
   private Integer stepCount;

   @Column(name="record_date")
   private LocalDate recordDate;

   public void updateStepCount(int newCount) {
        this.stepCount = newCount;
   }
}
