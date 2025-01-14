package employee_management_app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import employee_management_app.model.AppUser;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    @Test
    void findByUserName_ShouldReturnUser() {
        // When
        Optional<AppUser> found = userRepository.findOptionalByUsername("john.doe");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUserName()).isEqualTo("john.doe");
    }

    @Test
    void existsByUserName_ShouldReturnTrue() {
        // When
        boolean exists = userRepository.existsByUsername("john.doe");

        // Then
        assertThat(exists).isTrue();
    }
}