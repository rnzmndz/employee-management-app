package employee_management_app.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.dto.mapper.LeaveRequestMapper;
import employee_management_app.exception.ResourceNotFoundException;
import employee_management_app.model.Employee;
import employee_management_app.model.LeaveRequest;
import employee_management_app.model.enums.LeaveRequestStatus;
import employee_management_app.repository.EmployeeRepository;
import employee_management_app.repository.LeaveRequestRepository;
import employee_management_app.service.LeaveRequestService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LeaveRequestServiceImpl implements LeaveRequestService{
	
	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private LeaveRequestMapper leaveRequestMapper;

	@Override
	public LeaveRequestDTO submitLeaveRequest(Long employeeId, LeaveRequestDTO leaveRequestDTO) {
//		The paramaters should be valid and not null
		if(employeeId == null || employeeId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));

//		Convert LeaveRequestDTO into LeaveRequest entity
		LeaveRequest leaveRequest = leaveRequestMapper.toEntity(leaveRequestDTO);
		
//		Check if attendance employeeId is equals to our employeeId
		if (employee.getId() != leaveRequest.getEmployee().getId()) {
			leaveRequest.setEmployee(employee);
		}
		
//		Save the attendance in database
		leaveRequestRepository.save(leaveRequest);
		
		return leaveRequestMapper.toDTO(leaveRequest);
	}

	@Override
	public Page<LeaveRequestDTO> getEmployeeLeaveRequests(Long employeeId, Pageable pageable) {
//		The paramaters should be valid and not null
		if(employeeId == null || employeeId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));
		
//		Get all the attendance between those dates
		Page<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployee(employee, pageable);
		
//		Convert the Leave Request entity into 
		List<LeaveRequestDTO> leaveRequestDTOs = leaveRequestMapper.toDtoList(leaveRequests.getContent());
		
		return new PageImpl<>(leaveRequestDTOs, pageable, leaveRequests.getTotalElements());
	
	}

	@Override
	public LeaveRequestDTO updateLeaveRequestStatus(Long requestId, LeaveRequestStatus status) {

//		The paramaters should be valid and not null
		if(requestId == null || requestId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find leaveRequest and throw ResourceNotFoundException if it is not existing
		LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
				.orElseThrow(() -> new ResourceNotFoundException("Leave Request does not exist with ID: " + requestId));
		
//		Update the Leave Request Status
		leaveRequest.setStatus(status);
		
		return leaveRequestMapper.toDTO(leaveRequest);
	}

	@Override
	public boolean isLeaveAllowed(Long employeeId, LocalDate startDate, LocalDate endDate) {
		// TODO Create isLeaveAllowed method
		return false;
	}

}
