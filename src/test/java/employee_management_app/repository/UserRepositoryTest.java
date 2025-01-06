package employee_management_app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import employee_management_app.model.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserName_ShouldReturnUser() {
        // Given
        User user = new User();
        user.setUserName("testuser");
        userRepository.save(user);

        // When
        Optional<User> found = userRepository.findByUserName("testuser");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUserName()).isEqualTo("testuser");
    }

    @Test
    void existsByUserName_ShouldReturnTrue() {
        // Given
        User user = new User();
        user.setUserName("testuser");
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByUserName("testuser");

        // Then
        assertThat(exists).isTrue();
    }
}