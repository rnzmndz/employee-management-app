package employee_management_app.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import employee_management_app.dto.mapper.ScheduleMapper;
import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.exception.ResourceNotFoundException;
import employee_management_app.model.Employee;
import employee_management_app.model.Schedule;
import employee_management_app.repository.EmployeeRepository;
import employee_management_app.repository.ScheduleRepository;
import employee_management_app.service.ScheduleService;

public class ScheduleServiceImpl implements ScheduleService{
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ScheduleMapper scheduleMapper;

	@Override
	public ScheduleDTO assignSchedule(Long employeeId, ScheduleDTO scheduleDTO) {
//		The paramaters should be valid and not null
		if(employeeId == null || employeeId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));
		
//		Convert ScheduleDTO into Schedule entity
		Schedule schedule = scheduleMapper.toEntity(scheduleDTO);
		
//		Check if the assigned schedule is same as the employee ID
		if(employee.getId() != schedule.getEmployee().getId()) {
			schedule.setEmployee(employee);
		}
		
//		Save the schedule
		scheduleRepository.save(schedule);
		
		return scheduleMapper.toDTO(schedule);
	}

	@Override
	public List<ScheduleDTO> getEmployeeSchedules(Long employeeId, LocalDate startDate, LocalDate endDate) {
//		The paramaters should be valid and not null
		if(employeeId == null || employeeId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));

//		Find all Schedule for specifice employee
		Page<Schedule> schedules = scheduleRepository.findByEmployee(employee);
		
//		Filter and convert the list into ScheduleDTO
		return scheduleMapper.toDtoList(
				schedules.getContent().stream()
				.filter(schedule -> schedule.getDate().isAfter(startDate))
				.filter(schedule -> schedule.getDate().isBefore(endDate))
				.collect(Collectors.toList()));
	}

	@Override
	public boolean hasScheduleConflict(Long employeeId, ScheduleDTO newSchedule) {
//		The paramaters should be valid and not null
		if(employeeId == null || employeeId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));

//		Get all the schedules and convert it to ScheduleDTO
		List<ScheduleDTO> scheduleDTOs = scheduleMapper.toDtoList(new ArrayList<>(employee.getSchedules()));
		
//		Check if there is a schedule date same as the newSchedule date
		return !scheduleDTOs.stream().anyMatch(schedule -> schedule.getDate().equals(newSchedule.getDate()));
	}

}
