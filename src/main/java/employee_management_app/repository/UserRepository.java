package employee_management_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import employee_management_app.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserName(String username);
	Boolean existsByUserName(String username);
}
