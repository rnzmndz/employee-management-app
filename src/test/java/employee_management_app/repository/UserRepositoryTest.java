package employee_management_app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import employee_management_app.dto.mapper.user.UserCreateMapper;
import employee_management_app.dto.user.UserCreateDTO;
import employee_management_app.model.AppUser;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserCreateMapper userCreateMapper;
    
    @BeforeEach
    void setUp() {
    	userRepository.deleteAll(); 
    	UserCreateDTO user = UserCreateDTO.builder() 
    			.username("john.doe") 
    			.password("password123") 
    			.employeeId(1L)
    			.roles(Set.of("USER")) 
    			.build(); 
    	userRepository.save(userCreateMapper.toEntity(user));
    	
    }
    
    @Test
    void findByUserName_ShouldReturnUser() {
        // When
        Optional<AppUser> found = userRepository.findOptionalByUsername("john.doe");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("john.doe");
    }

    @Test
    void existsByUserName_ShouldReturnTrue() {
        // When
        boolean exists = userRepository.existsByUsername("john.doe");

        // Then
        assertThat(exists).isTrue();
    }
}