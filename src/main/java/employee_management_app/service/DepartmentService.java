package employee_management_app.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import employee_management_app.dto.department.DepartmentDTO;
import employee_management_app.dto.employee.EmployeeDetailDTO;
import employee_management_app.dto.employee.EmployeeListDTO;
import employee_management_app.exception.ResourceNotFoundException;

public interface DepartmentService {
    /**
     * Creates a new department.
     *
     * @param dto DTO containing new department information
     * @return DepartmentDTO containing created department details
     * @throws IllegalArgumentException if required fields are missing
     */
    DepartmentDTO createDepartment(DepartmentDTO dto);

    /**
     * Transfers an employee to a new department.
     * Migrated from EmployeeService
     *
     * @param employeeId Employee ID to transfer
     * @param newDepartmentId ID of the destination department
     * @return EmployeeDetailDTO with updated department information
     * @throws ResourceNotFoundException if employee or department not found
     * @throws IllegalStateException if transfer is not allowed
     */
    EmployeeDetailDTO transferEmployee(Long employeeId, Long newDepartmentId);

    /**
     * Finds employees in a specific department.
     * Migrated from EmployeeService
     *
     * @param departmentName Name of the department to search
     * @param pageable Pagination information
     * @return Page of EmployeeListDTO in the specified department
     */
    Page<EmployeeListDTO> findEmployeesByDepartment(String departmentName, Pageable pageable);

    /**
     * Finds employees in a specific department and position.
     * Migrated from EmployeeService
     *
     * @param departmentId Department ID to search
     * @param position Position title to filter by
     * @return List of EmployeeListDTO matching the criteria
     */
    List<EmployeeListDTO> findEmployeesByDepartmentAndPosition(Long departmentId, String position);

    /**
     * Gets employee count statistics grouped by department.
     * Migrated from EmployeeService
     *
     * @return Map of department names to employee counts
     */
    Map<String, Integer> getEmployeeStatsByDepartment();
}