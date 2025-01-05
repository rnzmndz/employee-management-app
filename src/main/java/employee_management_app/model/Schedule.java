package employee_management_app.model;

import java.beans.Transient;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule", indexes = {
		@Index(name = "idx_schedule_employee_date", columnList = "employee_id, date"),
		@Index(name = "idx_schedule_date", columnList = "date")
})
@Entity
@EqualsAndHashCode(of = "id")
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	@Column(name = "shift_start_time", nullable = false)
	private LocalTime shiftStartTime;
	
	@Column(name = "shift_end_time", nullable = false)
	private LocalTime shiftEndTime;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @AssertTrue(message = "Shift end time must be after start time")
    private boolean isShiftEndTimeAfterStartTime() {
        return shiftEndTime == null || shiftStartTime == null 
               || shiftEndTime.isAfter(shiftStartTime);
    }

    @Transient
    public Duration getShiftDuration() {
        return Duration.between(shiftStartTime, shiftEndTime);
    }
}
