package employee_management_app.service;

import java.time.LocalDate;
import java.util.List;

import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.exception.ResourceNotFoundException;

public interface ScheduleService {
    /**
     * Assigns a schedule to an employee.
     * Migrated from EmployeeService
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
     * Migrated from EmployeeService
     *
     * @param employeeId Employee ID to get schedules for
     * @param startDate Start of the date range
     * @param endDate End of the date range
     * @return List of ScheduleDTO for the specified period
     */
    List<ScheduleDTO> getEmployeeSchedules(Long employeeId, LocalDate startDate, 
            LocalDate endDate);

    /**
     * Checks for any scheduling conflicts.
     *
     * @param employeeId Employee ID to check
     * @param newSchedule Proposed new schedule
     * @return true if conflict exists, false otherwise
     */
    boolean hasScheduleConflict(Long employeeId, ScheduleDTO newSchedule);
}