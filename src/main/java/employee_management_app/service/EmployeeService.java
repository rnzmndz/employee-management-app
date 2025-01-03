package employee_management_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.dto.employee.EmployeeCreateDTO;
import employee_management_app.dto.employee.EmployeeDetailDTO;
import employee_management_app.dto.employee.EmployeeListDTO;
import employee_management_app.dto.employee.EmployeeUpdateDTO;
import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.model.enums.EmployeeStatus;

public interface EmployeeService {

	// CRUD Operations
    EmployeeDetailDTO createEmployee(EmployeeCreateDTO createEmployeeDTO);
    EmployeeDetailDTO updateEmployee(Long id, EmployeeUpdateDTO updateEmployeeDTO);
    void deleteEmployee(Long id);
    EmployeeDetailDTO getEmployeeById(Long id);
    Page<EmployeeListDTO> getAllEmployees(Pageable pageable);
    
    // Search and Filter Operations
    Page<EmployeeListDTO> searchEmployees(String keyword, Pageable pageable);
    Page<EmployeeListDTO> findByDepartment(String departmentName, Pageable pageable);
    Page<EmployeeListDTO> findByStatus(EmployeeStatus status, Pageable pageable);
    
    // Employee Status Management
    EmployeeDetailDTO updateEmployeeStatus(Long id, EmployeeStatus status);
    boolean isEmployeeActive(Long id);
    
    // Department Related Operations
    EmployeeDetailDTO transferDepartment(Long employeeId, Long newDepartmentId);
    List<EmployeeListDTO> findEmployeesByDepartmentAndPosition(Long departmentId, String position);
    
    // Attendance Management
    void recordAttendance(Long employeeId, AttendanceDTO attendanceDTO);
    Page<AttendanceDTO> getEmployeeAttendance(Long employeeId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    
    // Leave Management
    LeaveRequestDTO submitLeaveRequest(Long employeeId, LeaveRequestDTO leaveRequestDTO);
    Page<LeaveRequestDTO> getEmployeeLeaveRequests(Long employeeId, Pageable pageable);
    
    // Schedule Management
    ScheduleDTO assignSchedule(Long employeeId, ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getEmployeeSchedules(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);
    
    // User Account Management
//    void updateEmployeeCredentials(Long employeeId, EmployeeCredentialsDTO credentialsDTO);
//    void updateEmployeePassword(Long employeeId, EmployeePasswordUpdateDTO passwordUpdateDTO);
//    boolean validateEmployeeCredentials(String email, String password);
    
    // Validation and Verification
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    void validateEmployeeTransfer(Long employeeId, Long newDepartmentId);
    
    // Reporting
    Map<String, Integer> getEmployeeStatsByDepartment();
    Map<EmployeeStatus, Long> getEmployeeCountByStatus();
    List<EmployeeListDTO> findEmployeesHiredBetween(LocalDateTime startDate, LocalDateTime endDate);
}
