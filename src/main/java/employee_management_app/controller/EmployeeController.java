package employee_management_app.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import employee_management_app.dto.employee.EmployeeCreateDTO;
import employee_management_app.dto.employee.EmployeeDTO;
import employee_management_app.dto.employee.EmployeeDetailDTO;
import employee_management_app.dto.employee.EmployeeListDTO;
import employee_management_app.dto.employee.EmployeeUpdateDTO;
import employee_management_app.model.enums.EmployeeStatus;
import employee_management_app.service.EmployeeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping
	public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO employeeDTO) {
		return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDetailDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
		return ResponseEntity.ok(employeeService.updateEmployee(id, employeeUpdateDTO));
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<EmployeeDetailDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeListDTO>> getAllEmployees(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeListDTO>> searchEmployees(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(employeeService.searchEmployees(keyword, pageable));
    }

    @GetMapping("/department/{departmentName}")
    public ResponseEntity<Page<EmployeeListDTO>> findByDepartment(@PathVariable String departmentName, Pageable pageable) {
        return ResponseEntity.ok(employeeService.findByDepartment(departmentName, pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<EmployeeListDTO>> findByStatus(@PathVariable EmployeeStatus status, Pageable pageable) {
        return ResponseEntity.ok(employeeService.findByStatus(status, pageable));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EmployeeDetailDTO> updateEmployeeStatus(@PathVariable Long id, 
                                                                @RequestParam EmployeeStatus status) {
        return ResponseEntity.ok(employeeService.updateEmployeeStatus(id, status));
    }

    @GetMapping("/{id}/active")
    public ResponseEntity<Boolean> isEmployeeActive(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.isEmployeeActive(id));
    }

    @PutMapping("/{employeeId}/department/{departmentId}")
    public ResponseEntity<EmployeeDetailDTO> transferDepartment(@PathVariable Long employeeId, 
                                                              @PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.transferDepartment(employeeId, departmentId));
    }

    @GetMapping("/department/{departmentId}/position/{position}")
    public ResponseEntity<List<EmployeeListDTO>> findEmployeesByDepartmentAndPosition(
            @PathVariable Long departmentId, 
            @PathVariable String position) {
        return ResponseEntity.ok(employeeService.findEmployeesByDepartmentAndPosition(departmentId, position));
    }

    @GetMapping("/stats/department")
    public ResponseEntity<Map<String, Integer>> getEmployeeStatsByDepartment() {
        return ResponseEntity.ok(employeeService.getEmployeeStatsByDepartment());
    }

    @GetMapping("/stats/status")
    public ResponseEntity<Map<EmployeeStatus, Long>> getEmployeeCountByStatus() {
        return ResponseEntity.ok(employeeService.getEmployeeCountByStatus());
    }

    @GetMapping("/hired")
    public ResponseEntity<List<EmployeeListDTO>> findEmployeesHiredBetween(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(employeeService.findEmployeesHiredBetween(startDate, endDate));
    }

}
