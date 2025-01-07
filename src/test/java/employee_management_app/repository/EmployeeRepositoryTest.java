package employee_management_app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import employee_management_app.model.Employee;
import employee_management_app.model.enums.EmployeeStatus;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findByFirstName_ShouldReturnEmployee() {
    	
        // When
        Page<Employee> found = employeeRepository.findByFirstNameContaining("JoH", PageRequest.of(0, 10));

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.getContent().get(0).getFirstName()).isEqualTo("John");
    }
    
    @Test
    void findByLastName_ShouldReturnEmployee() {
    	
        // When
        Page<Employee> found = employeeRepository.findByLastNameContaining("DO", PageRequest.of(0, 10));

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.getContent().get(0).getLastName()).isEqualTo("Doe");
    }

    @Test
    void findByDepartmentName_ShouldReturnEmployees() {
    	
        // When
        Page<Employee> found = employeeRepository.findByDepartment_NameContaining("FI", PageRequest.of(0, 10));

        // Then
        assertThat(found).isNotEmpty();
        assertThat(found.getContent().size()).isEqualTo(3);
        assertThat(found.getContent().get(0).getDepartment().getName()).isEqualTo("Finance");
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
        assertThat(found.getContent().size()).isEqualTo(10);
        assertThat(found.getContent().get(0).getStatus()).isEqualTo(EmployeeStatus.ACTIVE);
    }
    
    @Test
    void findByPosition_ShouldReturnEmployees() {
    	
    	// When
    	List<Employee> employees = employeeRepository.findByPositionContaining("MAN");
    	
    	// Then
    	assertThat(employees).isNotEmpty();
    	assertThat(employees.size()).isEqualTo(2);
    }
}