package employee_management_app.model;

import java.beans.Transient;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "attendance", 
indexes = {
    @Index(name = "idx_attendance_employee_date", 
           columnList = "employee_id, date", 
           unique = true)
}
)
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
	private LocalDate date;
	
	@Column(name = "time_in")
	private LocalDateTime timeIn;
	
	@Column(name = "time_out")
	private LocalDateTime timeOut;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private AttendanceStatus status;
	
	@AssertTrue(message = "Time out must be after time in")
    private boolean isTimeOutAfterTimeIn() {
        return timeOut == null || timeIn == null || timeOut.isAfter(timeIn);
    }
	
	@Transient
	public Duration getWorkDuration() {
	    if (timeIn == null || timeOut == null) {
	        return Duration.ZERO;
	    }
	    return Duration.between(timeIn, timeOut);
	}
	
	public void setTimeIn(LocalDateTime timeIn) {
	    this.timeIn = timeIn;
	    if (this.date == null && timeIn != null) {
	        this.date = timeIn.toLocalDate();
	    }
	}
}
