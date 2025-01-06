package employee_management_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import employee_management_app.model.Employee;
import employee_management_app.model.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>{

	Page<LeaveRequest> findByEmployee(Employee employee, Pageable pageable);
}
