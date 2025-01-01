package employee_management_app.model;

import java.beans.Transient;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import employee_management_app.model.enums.LeaveRequestStatus;
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
@Table(name = "leave_request", indexes = {
		@Index(name = "idx_leave_employee", columnList = "employee_id"),
		@Index(name = "idx_leave_dates", columnList = "start_date, end_date"),
		@Index(name = "idx_leave_status", columnList = "status")
})
@Entity
@EqualsAndHashCode(of = "id")
public class LeaveRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;
	
	@CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
	
	@LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
	
	@Column(name = "reason", nullable = false)
	private String reason;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private LeaveRequestStatus status;
	
	@Column(name = "applied_date")
	private LocalDateTime appliedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approved_by")
	private Employee approvedBy;
	
	@AssertTrue(message = "End date must be after Start Date")
	private boolean isEndDateAfterStartDate() {
		return startDate == null || endDate == null
				|| endDate.isAfter(startDate);
	}
	
	@AssertTrue(message = "Leave duration cannot exceed 30 days")
	private boolean isLeaveDurationValid() {
	    if (startDate == null || endDate == null) return true;
	    return getWorkingDays() <= 30;  // adjust number as per your business rules
	}
	
	@Transient
	public Period getDayDuration() {
		return Period.between(startDate, endDate);
	}
	
	 @Transient
	    public long getWorkingDays() {
	        return startDate.datesUntil(endDate.plusDays(1))
	                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY 
	                    && date.getDayOfWeek() != DayOfWeek.SUNDAY)
	                .count();
	    }
}
