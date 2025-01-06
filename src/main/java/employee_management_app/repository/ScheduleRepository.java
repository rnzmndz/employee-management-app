package employee_management_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import employee_management_app.model.Employee;
import employee_management_app.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

	List<Schedule> findByEmployee(Employee employee);
}
