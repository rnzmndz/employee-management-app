package employee_management_app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.dto.employee.EmployeeCreateDTO;
import employee_management_app.dto.employee.EmployeeDTO;
import employee_management_app.dto.employee.EmployeeDetailDTO;
import employee_management_app.dto.employee.EmployeeListDTO;
import employee_management_app.dto.employee.EmployeeUpdateDTO;
import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.model.enums.EmployeeStatus;

public interface EmployeeService {

	 /**
     * Creates a new employee in the system.
     *
     * @param createEmployeeDTO DTO containing the new employee's information
     * @return EmployeeDTO containing the created employee's information
     * @throws IllegalArgumentException if required fields are missing or invalid
     */
    EmployeeDTO createEmployee(EmployeeCreateDTO createEmployeeDTO);

    /**
     * Updates an existing employee's information.
     *
     * @param id Employee ID to update
     * @param updateEmployeeDTO DTO containing the updated employee information
     * @return EmployeeDetailDTO containing the updated employee details
     * @throws ResourceNotFoundException if employee not found
     * @throws IllegalArgumentException if update data is null
     */
    EmployeeDetailDTO updateEmployee(Long id, EmployeeUpdateDTO updateEmployeeDTO);

    /**
     * Deletes an employee from the system.
     *
     * @param id Employee ID to delete
     * @throws ResourceNotFoundException if employee not found
     * @throws IllegalStateException if employee cannot be deleted
     */
    void deleteEmployee(Long id);

    /**
     * Retrieves detailed information about an employee.
     *
     * @param id Employee ID to retrieve
     * @return EmployeeDetailDTO containing the employee's detailed information
     * @throws ResourceNotFoundException if employee not found
     */
    EmployeeDetailDTO getEmployeeById(Long id);

    /**
     * Retrieves a paginated list of all employees.
     *
     * @param pageable Pagination information
     * @return Page of EmployeeListDTO containing basic employee information
     */
    Page<EmployeeListDTO> getAllEmployees(Pageable pageable);

    /**
     * Searches for employees based on a keyword.
     *
     * @param keyword Search term to match against employee fields
     * @param pageable Pagination information
     * @return Page of EmployeeListDTO matching the search criteria
     */
    Page<EmployeeListDTO> searchEmployees(String keyword, Pageable pageable);

    /**
     * Finds employees in a specific department.
     *
     * @param departmentName Name of the department to search
     * @param pageable Pagination information
     * @return Page of EmployeeListDTO in the specified department
     */
    Page<EmployeeListDTO> findByDepartment(String departmentName, Pageable pageable);

    /**
     * Finds employees with a specific status.
     *
     * @param status EmployeeStatus to filter by
     * @param pageable Pagination information
     * @return Page of EmployeeListDTO with the specified status
     */
    Page<EmployeeListDTO> findByStatus(EmployeeStatus status, Pageable pageable);

    /**
     * Updates an employee's status.
     *
     * @param id Employee ID to update
     * @param status New EmployeeStatus to set
     * @return EmployeeDetailDTO with updated status
     * @throws ResourceNotFoundException if employee not found
     */
    EmployeeDetailDTO updateEmployeeStatus(Long id, EmployeeStatus status);

    /**
     * Checks if an employee is currently active.
     *
     * @param id Employee ID to check
     * @return true if employee is active, false otherwise
     * @throws ResourceNotFoundException if employee not found
     */
    boolean isEmployeeActive(Long id);

    /**
     * Transfers an employee to a new department.
     *
     * @param employeeId Employee ID to transfer
     * @param newDepartmentId ID of the destination department
     * @return EmployeeDetailDTO with updated department information
     * @throws ResourceNotFoundException if employee or department not found
     * @throws IllegalStateException if transfer is not allowed
     */
    EmployeeDetailDTO transferDepartment(Long employeeId, Long newDepartmentId);

    /**
     * Finds employees in a specific department and position.
     *
     * @param departmentId Department ID to search
     * @param position Position title to filter by
     * @return List of EmployeeListDTO matching the criteria
     */
    List<EmployeeListDTO> findEmployeesByDepartmentAndPosition(Long departmentId, String position);

    /**
     * Records attendance for an employee.
     *
     * @param employeeId Employee ID to record attendance for
     * @param attendanceDTO Attendance information to record
     * @throws ResourceNotFoundException if employee not found
     * @throws IllegalArgumentException if attendance data is invalid
     */
    void recordAttendance(Long employeeId, AttendanceDTO attendanceDTO);

    /**
     * Retrieves attendance records for an employee within a date range.
     *
     * @param employeeId Employee ID to get attendance for
     * @param startDate Start of the date range
     * @param endDate End of the date range
     * @param pageable Pagination information
     * @return Page of AttendanceDTO for the specified period
     */
    Page<AttendanceDTO> getEmployeeAttendance(Long employeeId, LocalDateTime startDate, 
            LocalDateTime endDate, Pageable pageable);

    /**
     * Submits a leave request for an employee.
     *
     * @param employeeId Employee ID requesting leave
     * @param leaveRequestDTO Leave request information
     * @return LeaveRequestDTO containing the submitted request
     * @throws ResourceNotFoundException if employee not found
     * @throws IllegalArgumentException if request is invalid
     */
    LeaveRequestDTO submitLeaveRequest(Long employeeId, LeaveRequestDTO leaveRequestDTO);

    /**
     * Retrieves leave requests for an employee.
     *
     * @param employeeId Employee ID to get requests for
     * @param pageable Pagination information
     * @return Page of LeaveRequestDTO for the employee
     */
    Page<LeaveRequestDTO> getEmployeeLeaveRequests(Long employeeId, Pageable pageable);

    /**
     * Assigns a schedule to an employee.
     *
     * @param employeeId Employee ID to assign schedule to
     * @param scheduleDTO Schedule information to assign
     * @return ScheduleDTO containing the assigned schedule
     * @throws ResourceNotFoundException if employee not found
     * @throws IllegalArgumentException if schedule is invalid
     */
    ScheduleDTO assignSchedule(Long employeeId, ScheduleDTO scheduleDTO);

    /**
     * Retrieves schedules for an employee within a date range.
     *
     * @param employeeId Employee ID to get schedules for
     * @param startDate Start of the date range
     * @param endDate End of the date range
     * @return List of ScheduleDTO for the specified period
     */
    List<ScheduleDTO> getEmployeeSchedules(Long employeeId, LocalDate startDate, 
            LocalDate endDate);

    /**
     * Checks if an email address is already registered to an employee.
     *
     * @param email Email address to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a phone number is already registered to an employee.
     *
     * @param phone Phone number to check
     * @return true if phone exists, false otherwise
     */
    boolean existsByPhone(String phone);

    /**
     * Validates if an employee can be transferred to a new department.
     *
     * @param employeeId Employee ID to validate
     * @param newDepartmentId Target department ID
     * @throws ResourceNotFoundException if employee or department not found
     * @throws IllegalStateException if transfer is not allowed
     */
//    void validateEmployeeTransfer(Long employeeId, Long newDepartmentId);

    /**
     * Gets employee count statistics grouped by department.
     *
     * @return Map of department names to employee counts
     */
    Map<String, Integer> getEmployeeStatsByDepartment();

    /**
     * Gets employee count statistics grouped by employee status.
     *
     * @return Map of EmployeeStatus to employee counts
     */
    Map<EmployeeStatus, Long> getEmployeeCountByStatus();

    /**
     * Finds employees hired within a specific date range.
     *
     * @param startDate Start of the hire date range
     * @param endDate End of the hire date range
     * @return List of EmployeeListDTO hired within the specified period
     */
    List<EmployeeListDTO> findEmployeesHiredBetween(LocalDateTime startDate, LocalDateTime endDate);

    // User Account Management
//    void updateEmployeeCredentials(Long employeeId, EmployeeCredentialsDTO credentialsDTO);
//    void updateEmployeePassword(Long employeeId, EmployeePasswordUpdateDTO passwordUpdateDTO);
//    boolean validateEmployeeCredentials(String email, String password);
    
    }
