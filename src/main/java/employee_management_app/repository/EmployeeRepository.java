package employee_management_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import employee_management_app.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
