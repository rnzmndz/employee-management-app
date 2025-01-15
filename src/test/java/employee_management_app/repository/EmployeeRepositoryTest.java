package employee_management_app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import employee_management_app.dto.department.DepartmentDTO;
import employee_management_app.dto.employee.EmployeeCreateDTO;
import employee_management_app.dto.mapper.DepartmentMapper;
import employee_management_app.dto.mapper.employee.EmployeeCreateMapper;
import employee_management_app.model.Department;
import employee_management_app.model.Employee;
import employee_management_app.model.enums.EmployeeStatus;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    
    @Autowired
    private EmployeeCreateMapper employeeCreateMapper;
    
    @Autowired
    private DepartmentMapper departmentMapper;

    private Employee employee1;
    private Employee employee2;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
    	leaveRequestRepository.deleteAll();
    	attendanceRepository.deleteAll();
    	userRepository.deleteAll();
    	employeeRepository.deleteAll();
    	departmentRepository.deleteAll();
    	
    	DepartmentDTO departmentDTO1 = new DepartmentDTO(1L, "IT", "Sample IT");
    	
    	Department department1 = departmentRepository.save(departmentMapper.toEntity(departmentDTO1)); 

        // Create test employees
        EmployeeCreateDTO employeeDTO1 = EmployeeCreateDTO.builder()
        		.firstName("John")
        		.lastName("Doe")
        		.email("john.doe@company.com")
        		.phone("1234567890")
        		.position("Software Engineer")
        		.departmentId(department1.getId())
        		.role("Engineer")
        		.dateHired(LocalDateTime.now())
        		.build();
        
        EmployeeCreateDTO employeeDTO2 = EmployeeCreateDTO.builder()
        		.firstName("Jane")
        		.lastName("Smith")
        		.email("jane.smith@company.com")
        		.phone("0987654321")
        		.position("Senior Developer")
        		.departmentId(department1.getId())
        		.role("Engineer")
        		.dateHired(LocalDateTime.now())
        		.build();

        employee1 = employeeCreateMapper.toEntity(employeeDTO1);
        employee2 = employeeCreateMapper.toEntity(employeeDTO2);
        
        // Save test data
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // Initialize pageable
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findAll_ShouldReturnAllEmployees() {
        Page<Employee> result = employeeRepository.findAll(pageable);
        
        assertNotNull(result);
        assertTrue(result.getContent().size() >= 2);
        assertTrue(result.getContent().contains(employee1));
        assertTrue(result.getContent().contains(employee2));
    }

    @Test
    void findByFirstNameContaining_ShouldReturnMatchingEmployees() {
        Page<Employee> result = employeeRepository.findByFirstNameContaining("John", pageable);
        
        assertNotNull(result);
        assertTrue(result.getContent().contains(employee1));
        assertFalse(result.getContent().contains(employee2));
    }

    @Test
    void findByLastNameContaining_ShouldReturnMatchingEmployees() {
        Page<Employee> result = employeeRepository.findByLastNameContaining("Smith", pageable);
        
        assertNotNull(result);
        assertTrue(result.getContent().contains(employee2));
        assertFalse(result.getContent().contains(employee1));
    }

    @Test
    void findByDepartmentNameContaining_ShouldReturnMatchingEmployees() {
        Page<Employee> result = employeeRepository.findByDepartment_NameContaining("IT", pageable);
        
        assertNotNull(result);
        assertTrue(result.getContent().contains(employee1));
        assertTrue(result.getContent().contains(employee2));
    }

    @Test
    void findByStatus_ShouldReturnMatchingEmployees() {
        Page<Employee> result = employeeRepository.findByStatus(EmployeeStatus.ACTIVE, pageable);
        
        assertNotNull(result);
        assertTrue(result.getContent().contains(employee1));
        assertTrue(result.getContent().contains(employee2));
    }

    @Test
    void findByPositionContaining_ShouldReturnMatchingEmployees() {
        Page<Employee> result = employeeRepository.findByPositionContaining("Engineer", pageable);
        
        assertNotNull(result);
        assertTrue(result.getContent().contains(employee1));
        assertFalse(result.getContent().contains(employee2));
    }

    @Test
    void findByDepartmentNameContainingList_ShouldReturnMatchingEmployees() {
        List<Employee> result = employeeRepository.findByDepartment_NameContaining("IT");
        
        assertNotNull(result);
        assertTrue(result.contains(employee1));
        assertTrue(result.contains(employee2));
        assertEquals(2, result.size());
    }

    @Test
    void findByPositionContainingList_ShouldReturnMatchingEmployees() {
        List<Employee> result = employeeRepository.findByPositionContaining("Developer");
        
        assertNotNull(result);
        assertTrue(result.contains(employee2));
        assertFalse(result.contains(employee1));
        assertEquals(1, result.size());
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenEmailExists() {
        assertTrue(employeeRepository.existsByEmail("john.doe@company.com"));
        assertFalse(employeeRepository.existsByEmail("nonexistent@company.com"));
    }

    @Test
    void existsByPhone_ShouldReturnTrue_WhenPhoneExists() {
        assertTrue(employeeRepository.existsByPhone("1234567890"));
        assertFalse(employeeRepository.existsByPhone("9999999999"));
    }
}