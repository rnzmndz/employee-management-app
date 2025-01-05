package employee_management_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import employee_management_app.model.Employee;
import employee_management_app.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

	Page<Schedule> findByEmployee(Employee employee);
}
