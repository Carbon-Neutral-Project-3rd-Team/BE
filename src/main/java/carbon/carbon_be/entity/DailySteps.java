package carbon.carbon_be.entity;

import java.time.LocalDate;

public class DailySteps {
    private Long id;
    private Long userId;
    private int stepCount;
    private LocalDate recordDate;

    public DailySteps() {}

    public DailySteps(Long id, Long userId, int stepCount, LocalDate recordDate) {
        this.id = id;
        this.userId = userId;
        this.stepCount = stepCount;
        this.recordDate = recordDate;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getStepCount() { return stepCount; }
    public void setStepCount(int stepCount) { this.stepCount = stepCount; }

    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }
}
