package employee_management_app.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import employee_management_app.dto.employee.EmployeeCreateDTO;
import employee_management_app.dto.employee.EmployeeDTO;
import employee_management_app.dto.employee.EmployeeDetailDTO;
import employee_management_app.dto.employee.EmployeeListDTO;
import employee_management_app.dto.employee.EmployeeUpdateDTO;
import employee_management_app.dto.mapper.EntityUpdater;
import employee_management_app.dto.mapper.employee.EmployeeCreateMapper;
import employee_management_app.dto.mapper.employee.EmployeeDetailMapper;
import employee_management_app.dto.mapper.employee.EmployeeListMapper;
import employee_management_app.dto.mapper.employee.EmployeeMapper;
import employee_management_app.dto.mapper.employee.EmployeeUpdateMapper;
import employee_management_app.exception.ResourceNotFoundException;
import employee_management_app.model.Department;
import employee_management_app.model.Employee;
import employee_management_app.model.enums.EmployeeStatus;
import employee_management_app.repository.DepartmentRepository;
import employee_management_app.repository.EmployeeRepository;
import employee_management_app.service.EmployeeService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private EmployeeCreateMapper createMapper;
	@Autowired
	private EmployeeUpdateMapper updateMapper; 
	@Autowired
	private EmployeeDetailMapper detailMapper;
	@Autowired
	private EmployeeListMapper listMapper;
	
	@Override
	public EmployeeDTO createEmployee(EmployeeCreateDTO createEmployeeDTO) {
//		Validate dto
		if (createEmployeeDTO == null) {
			throw new IllegalArgumentException("UserDTO cannot be null");
		}
		
//		Convert EmployeeCreateDTO to entity and update its fields
		Employee employee = createMapper.toEntity(createEmployeeDTO);
		employee.setCreatedAt(LocalDateTime.now());
		employee.setUpdatedAt(LocalDateTime.now());
		
//		Save the created employee into database
		employeeRepository.save(employee);
		
		return employeeMapper.toDTO(employee);
	}

	@Override
	public EmployeeDetailDTO updateEmployee(Long id, EmployeeUpdateDTO updateEmployeeDTO) {
//		Check if the parameter is null
		if (updateEmployeeDTO == null) {
			throw new IllegalArgumentException("Updated user cannot be null");
		}
		
//		Find the employee in the database if it not existing throw a ResourceNotFoundException
		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not existing with ID: " + id));
		
//		Convert the existing Employee entity to EmployeeUpdateDTO
		EmployeeUpdateDTO exisitngEmployeeDTO = updateMapper.toDTO(existingEmployee);
		
//		update the existing Employee if the field is null the it will not update the existing field
		EntityUpdater.updateIfNotNull(exisitngEmployeeDTO, updateEmployeeDTO);
		
//		Update the Employee entity using DTO		
		updateMapper.updateEmployeeFromDto(exisitngEmployeeDTO, existingEmployee);
		
//		Update the database
		employeeRepository.save(existingEmployee);
		
//		Convert the entity into EmployeeDetailDTO
		EmployeeDetailDTO detailDTO = detailMapper.toDTO(existingEmployee);
		
		return detailDTO;
	}

	@Override
	public void deleteEmployee(Long id) {
//		Find the Employee
//		If the employee does not exist throw ResourceNotFoundException
		employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("\"Employee not existing with ID: " + id));
		
//		Delete the employee in database
		employeeRepository.deleteById(id);
	}

	@Override
	public EmployeeDetailDTO getEmployeeById(Long id) {
//		The paramater should be valid and not null
		if(id == null || id < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + id));
		
//		Convert and return EmployeeDetailDTO
		return detailMapper.toDTO(employee);
	}

	@Override
	public Page<EmployeeListDTO> getAllEmployees(Pageable pageable) {
//		Get all the Employees using repository
		Page<Employee> employeesPage = employeeRepository.findAll(pageable);
		
//		Convert it to EmployeeListDTO List
		List<EmployeeListDTO> employeeListDTOs = listMapper.toDtoList(employeesPage.getContent());
		
//		Convert it to be pageable then return it
		return new PageImpl<>(employeeListDTOs, pageable, employeesPage.getTotalPages());
	}

	@Override
	public Page<EmployeeListDTO> searchEmployees(String keyword, Pageable pageable) {
//		Get all the Employees by their firstname
		Page<Employee> employeesFirstNamePage = employeeRepository.findByFirstName(keyword, pageable);
		
//		Get all the Employees by their lastname
		Page<Employee> employeesLastNamePage = employeeRepository.findByLastName(keyword, pageable);

//		Combine the two page
		List<Employee> combinedEmployeeList = Stream.concat(employeesFirstNamePage.getContent().stream(), employeesLastNamePage.getContent().stream())
				.distinct()
				.collect(Collectors.toList());
		
//		Convert the list Employee list entity into EmployeeListDTO
		List<EmployeeListDTO> employeeListDTOs = listMapper.toDtoList(combinedEmployeeList);
		
//		Convert it to pageable then return it
		return new PageImpl<>(employeeListDTOs, pageable, combinedEmployeeList.size());
	}

	@Override
	public Page<EmployeeListDTO> findByDepartment(String departmentName, Pageable pageable) {
//		Find employees by Department Name
		Page<Employee> employees = employeeRepository.findByDepartment_Name(departmentName, pageable);
		
//		Convert the Employee pageable data into EmployeeListDTO
		List<EmployeeListDTO> employeeListDTOs = listMapper.toDtoList(employees.getContent());
		
		return new PageImpl<>(employeeListDTOs, pageable, employees.getTotalPages());
	}

	@Override
	public Page<EmployeeListDTO> findByStatus(EmployeeStatus status, Pageable pageable) {
//		Find employees by status
		Page<Employee> employees = employeeRepository.findByStatus(status, pageable);
		
//		Convert the Employee pageable data into EmployeeListDTO
		List<EmployeeListDTO> employeeListDTOs = listMapper.toDtoList(employees.getContent());
		
		return new PageImpl<>(employeeListDTOs, pageable, employees.getTotalPages());
	}

	@Override
	public EmployeeDetailDTO updateEmployeeStatus(Long id, EmployeeStatus status) {
//		The paramater should be valid and not null
		if(id == null || id < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + id));

//		update employee status then save it
		employee.setStatus(status);
		employeeRepository.save(employee);
		
		return detailMapper.toDTO(employee);
	}

	@Override
	public boolean isEmployeeActive(Long id) {
//		The paramater should be valid and not null
		if(id == null || id < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee in the database
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + id));

		return employee.getStatus() == EmployeeStatus.ACTIVE;
	}

	@Override
	public EmployeeDetailDTO transferDepartment(Long employeeId, Long newDepartmentId) {
//		The paramater should be valid and not null
		if(employeeId == null || employeeId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
		if(newDepartmentId == null || newDepartmentId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
//		Find the Employee and Department in the database
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + employeeId));
		
		Department department = departmentRepository.findById(newDepartmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department does not exist with ID: " + newDepartmentId));
		
//		Transferring Employee in other department
		employee.setDepartment(department);
		
//		Save the updated Employee
		employeeRepository.save(employee);
		
		return detailMapper.toDTO(employee);
	}

	@Override
	public List<EmployeeListDTO> findEmployeesByDepartmentAndPosition(Long departmentId, String position) {
//		The parameters should be valid and not null
		if(departmentId == null || departmentId < 0) {
			throw new IllegalArgumentException("ID must be valid and not null");
		}
		
		if (position == null) {
			throw new IllegalArgumentException("position must be not null");
		}
		
//		Find the department in the database
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department does not exist with ID: " + departmentId));
		
//		Find the employees
		List<Employee> employeesByDepartment = employeeRepository.findByDepartment_Name(department.getName());
		
		List<Employee> employeesByDeptAndPosition = employeesByDepartment.stream()
				.filter(employee -> employee.getPosition() == position)
				.collect(Collectors.toList());
		
		return listMapper.toDtoList(employeesByDeptAndPosition);
	}

	@Override
	public boolean existsByEmail(String email) {
		return employeeRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByPhone(String phone) {
		return employeeRepository.existsByPhone(phone);
	}


	@Override
	public Map<String, Integer> getEmployeeStatsByDepartment() {
		List<Employee> employees = employeeRepository.findAll();
	    
	    return employees.stream()
	        .filter(employee -> employee.getStatus() == EmployeeStatus.ACTIVE)
	        .collect(Collectors.groupingBy(
	            employee -> employee.getDepartment().getName(),
	            Collectors.collectingAndThen(
	                Collectors.counting(),
	                Long::intValue
	            )
	        ));
	}

	@Override
	public Map<EmployeeStatus, Long> getEmployeeCountByStatus() {
		 List<Employee> employees = employeeRepository.findAll();
		    
		    return employees.stream()
		        .collect(Collectors.groupingBy(
		            Employee::getStatus,
		            Collectors.counting()
		        ));
	}

	@Override
	public List<EmployeeListDTO> findEmployeesHiredBetween(LocalDateTime startDate, LocalDateTime endDate) {
		List<Employee> employees = employeeRepository.findAll();
		
		return listMapper.toDtoList(employees.stream()
				.filter(employee -> employee.getDateHired().isAfter(startDate))
				.filter(employee -> employee.getDateHired().isBefore(endDate))
				.collect(Collectors.toList()));
	}

}
