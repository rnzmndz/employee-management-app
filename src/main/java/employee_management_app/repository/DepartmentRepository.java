package employee_management_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import employee_management_app.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
