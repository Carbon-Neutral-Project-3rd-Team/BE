package carbon.carbon_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import carbon.carbon_be.entity.Users;


@Entity
@Table(name = "daily_steps")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailySteps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    private int stepCount;

    private LocalDate recordDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
