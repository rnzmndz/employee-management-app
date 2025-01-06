package employee_management_app.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.exception.ResourceNotFoundException;
import employee_management_app.model.enums.LeaveRequestStatus;

public interface LeaveRequestService {
    /**
     * Submits a new leave request for an employee.
     * Migrated from EmployeeService
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
     * Migrated from EmployeeService
     *
     * @param employeeId Employee ID to get requests for
     * @param pageable Pagination information
     * @return Page of LeaveRequestDTO for the employee
     */
    Page<LeaveRequestDTO> getEmployeeLeaveRequests(Long employeeId, Pageable pageable);

    /**
     * Updates the status of a leave request.
     *
     * @param requestId Leave request ID to update
     * @param status New status to set
     * @return Updated LeaveRequestDTO
     * @throws ResourceNotFoundException if request not found
     */
    LeaveRequestDTO updateLeaveRequestStatus(Long requestId, LeaveRequestStatus status);

    /**
     * Checks if an employee has available leave balance.
     *
     * @param employeeId Employee ID to check
     * @param startDate Start of requested leave
     * @param endDate End of requested leave
     * @return true if leave can be granted, false otherwise
     */
    boolean isLeaveAllowed(Long employeeId, LocalDate startDate, LocalDate endDate);
}