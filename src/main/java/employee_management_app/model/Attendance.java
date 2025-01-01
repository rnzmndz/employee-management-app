package employee_management_app.model;

import java.beans.Transient;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import employee_management_app.model.enums.AttendanceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "attendance", indexes = {
		@Index(name = "idx_attendance_employee", columnList = "employee_id"),
		@Index(name = "idx_attendance_date", columnList = "date")
})
@Entity
@EqualsAndHashCode(of = "id")
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@Column(name = "date", nullable = false)
	private LocalDateTime date;
	
	@Column(name = "time_in", nullable = false)
	private LocalDateTime timeIn;
	
	@Column(name = "time_out", nullable = false)
	private LocalDateTime timeOut;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private AttendanceStatus status;
	
	@CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
	
	@LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
	
	@AssertTrue(message = "Time out must be after time in")
    private boolean isTimeOutAfterTimeIn() {
        return timeOut == null || timeIn == null || timeOut.isAfter(timeIn);
    }
	
	@Transient
    public Duration getWorkDuration() {
        return Duration.between(timeIn, timeOut);
    }
}
