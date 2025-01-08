package employee_management_app.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import employee_management_app.dto.department.DepartmentDTO;
import employee_management_app.dto.employee.EmployeeDetailDTO;
import employee_management_app.dto.employee.EmployeeListDTO;
import employee_management_app.dto.mapper.DepartmentMapper;
import employee_management_app.dto.mapper.employee.EmployeeDetailMapper;
import employee_management_app.exception.ResourceNotFoundException;
import employee_management_app.model.Department;
import employee_management_app.model.Employee;
import employee_management_app.repository.DepartmentRepository;
import employee_management_app.repository.EmployeeRepository;
import employee_management_app.service.DepartmentService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Autowired
	private EmployeeDetailMapper detailMapper;
	
	@Override
	public DepartmentDTO createDepartment(DepartmentDTO dto) {
		
//		Check if the parameter is null
		if (dto == null) {
			new IllegalArgumentException("Department should be not null");
		}
		
//		Convert to Entity and then save
		Department department = departmentMapper.toEntity(dto);
		departmentRepository.save(department);
		
		return dto;
	}

	@Override
	public EmployeeDetailDTO transferEmployee(Long employeeId, Long newDepartmentId) {
//		The paramaters should be valid and not null
		if(employeeId == null || employeeId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));
		
//		Check if the newDepartment exist
		Optional<Department> department = departmentRepository.findById(newDepartmentId);
		if (department.isEmpty()) {
			throw new IllegalStateException("Cannot transfer department does not exist with ID: " + newDepartmentId);
		}

//		Transfer to another department
		employee.setDepartment(department.get());
		employee.setUpdatedAt(LocalDateTime.now());
		
		employeeRepository.save(employee);
		
		return detailMapper.toDTO(employee);
	}

	@Override
	public Page<EmployeeListDTO> findEmployeesByDepartment(String departmentName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeListDTO> findEmployeesByDepartmentAndPosition(Long departmentId, String position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Integer> getEmployeeStatsByDepartment() {
		// TODO Auto-generated method stub
		return null;
	}

}
