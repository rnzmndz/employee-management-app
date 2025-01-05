package employee_management_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import employee_management_app.model.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>{

}
