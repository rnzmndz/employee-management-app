package employee_management_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import employee_management_app.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
