package employee_management_app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import employee_management_app.model.Employee;
import employee_management_app.model.enums.EmployeeStatus;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Page<Employee> findAll(Pageable pageable);
	
	@Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))")
	Page<Employee> findByFirstNameContaining(@Param("firstName") String firstName, Pageable pageable);
	
	@Query("SELECT e FROM Employee e WHERE LOWER(e.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))")
	Page<Employee> findByLastNameContaining(@Param("lastName") String lastName, Pageable pageable);
	
	@Query("SELECT e FROM Employee e WHERE LOWER(e.department.name) LIKE LOWER(CONCAT('%', :departmentName, '%'))")
	Page<Employee> findByDepartment_NameContaining(@Param("departmentName") String departmentName, Pageable pageable);
	
	Page<Employee> findByStatus(EmployeeStatus status, Pageable pageable);
	
	@Query("SELECT e FROM Employee e WHERE LOWER(e.position) LIKE LOWER(CONCAT('%', :position, '%'))")
	Page<Employee> findByPositionContaining(@Param("position") String Position, Pageable pageable);
	
	@Query("SELECT e FROM Employee e WHERE LOWER(e.department.name) LIKE LOWER(CONCAT('%', :departmentName, '%'))")
	List<Employee> findByDepartment_NameContaining(@Param("departmentName") String departmentName);
	
	@Query("SELECT e FROM Employee e WHERE LOWER(e.position) LIKE LOWER(CONCAT('%', :position, '%'))")
	List<Employee> findByPositionContaining(@Param("position") String Position);
	
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);
}
