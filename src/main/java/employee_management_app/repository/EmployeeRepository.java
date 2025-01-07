package employee_management_app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import employee_management_app.model.Employee;
import employee_management_app.model.enums.EmployeeStatus;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Page<Employee> findAll(Pageable pageable);
	
	Page<Employee> findByFirstName(String firstName, Pageable pageable);
	Page<Employee> findByLastName(String lastName, Pageable pageable);
	Page<Employee> findByDepartment_Name(String departmentName, Pageable pageable);
	Page<Employee> findByStatus(EmployeeStatus status, Pageable pageable);
	Page<Employee> findByPosition(String Position, Pageable pageable);
	
	List<Employee> findByDepartment_Name(String departmentName);
	List<Employee> findByPosition(String Position);
	
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);
}
