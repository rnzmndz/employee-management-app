package employee_management_app.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.exception.ResourceNotFoundException;

//AttendanceService.java
public interface AttendanceService {
	 /**
	  * Records a new attendance entry for an employee.
	  *
	  * @param attendanceDTO DTO containing attendance information
	  * @throws ResourceNotFoundException if employee not found
	  * @throws IllegalArgumentException if attendance data is invalid
	  */
	 void recordAttendance(AttendanceDTO attendanceDTO);
	
	 /**
	  * Retrieves attendance records for an employee within a date range.
	  *
	  * @param employeeId Employee ID to get attendance for
	  * @param startDate Start of the date range
	  * @param endDate End of the date range
	  * @param pageable Pagination information
	  * @return Page of AttendanceDTO for the specified period
	  * @throws ResourceNotFoundException if employee not found
	  */
	 Page<AttendanceDTO> getAttendanceByDateRange(Long employeeId, LocalDate startDate, 
	         LocalDate endDate, Pageable pageable);
	
	 /**
	  * Generates attendance statistics for a given time period.
	  *
	  * @param startDate Start of the analysis period
	  * @param endDate End of the analysis period
	  * @return Map containing various attendance metrics
	  */
	 Map<String, Object> getAttendanceStatistics(LocalDate startDate, LocalDate endDate);
	 
	 }
