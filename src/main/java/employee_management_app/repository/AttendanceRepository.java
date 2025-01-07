package employee_management_app.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import employee_management_app.model.Attendance;

@Repository
public interface AttendanceRepository extends  JpaRepository<Attendance, Long> {

	@Query("SELECT a FROM Attendance a WHERE a.date BETWEEN :startDate AND :endDate")
	Page<Attendance> findByDateRange(
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate, 
			Pageable pageable
			);
}
