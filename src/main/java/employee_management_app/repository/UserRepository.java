package employee_management_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import employee_management_app.model.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>{

	Optional<AppUser> findByUserName(String username);
	
	Boolean existsByUserName(String username);
}
