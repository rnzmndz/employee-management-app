package employee_management_app.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.dto.mapper.AttendanceMapper;
import employee_management_app.model.Attendance;
import employee_management_app.repository.AttendanceRepository;
import employee_management_app.service.AttendanceService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private AttendanceMapper attendanceMapper;

	@Override
	public void recordAttendance(AttendanceDTO attendanceDTO) {
//		Convert attendanceDTO to attendance entity
		Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
		
//		Save the attendance in database
		attendanceRepository.save(attendance);
	}

	@Override
	public Page<AttendanceDTO> getAttendanceByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate,
			Pageable pageable) {
		
//		startDate should be earlier than endDate
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("Start Date Should be before the End Date");
		}
		
//		Get all the attendance between those dates
		Page<Attendance> attendances = attendanceRepository.findByDateRange(startDate, endDate, pageable);
		
//		Filter the attendance
		List<Attendance> filteredAttendance = attendances.getContent().stream()
				.filter(attendance -> attendance.getEmployee().getId() == employeeId)
				.collect(Collectors.toList());
			

//		Convert the Attendance List into AttendanceDTO List
		List<AttendanceDTO> attendanceDTOs = attendanceMapper.toList(filteredAttendance);
		
		return new PageImpl<>(attendanceDTOs, pageable, attendances.getTotalElements());
	}

	@Override
	public Map<String, Object> getAttendanceStatistics(LocalDate startDate, LocalDate endDate) {
		// TODO Do the getAttendanceStatistics method
		return null;
	}
	
	
}
