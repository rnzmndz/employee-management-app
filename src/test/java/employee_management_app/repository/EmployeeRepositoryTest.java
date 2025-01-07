package employee_management_app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import employee_management_app.model.Department;
import employee_management_app.model.Employee;
import employee_management_app.model.enums.EmployeeStatus;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void findByFirstName_ShouldReturnEmployee() {
    	
        // When
        Page<Employee> found = employeeRepository.findByFirstName("John", PageRequest.of(0, 10));

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.getContent().get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void findByDepartmentName_ShouldReturnEmployees() {
    	
        // When
        Page<Employee> found = employeeRepository.findByDepartment_Name("IT", PageRequest.of(0, 10));

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.getContent().get(0).getDepartment().getName()).isEqualTo("IT");
    }

    @Test
    void existsByEmail_ShouldReturnTrue() {
      
        // When
        boolean exists = employeeRepository.existsByEmail("john.doe@company.com");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void findByStatus_ShouldReturnEmployees() {
        
        // When
        Page<Employee> found = employeeRepository.findByStatus(EmployeeStatus.ACTIVE, PageRequest.of(0, 10));

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.getContent().get(0).getStatus()).isEqualTo(EmployeeStatus.ACTIVE);
    }
}