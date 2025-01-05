package employee_management_app.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import employee_management_app.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{

	@Query("SELECT e FROM Employee e WHERE e.hireDate BETWEEN :startDate AND :endDate")
	Page<Attendance> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
