package employee_management_app.dto.mapper.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import employee_management_app.dto.user.UserDTO;
import employee_management_app.model.Employee;
import employee_management_app.model.User;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testToDto() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Employee employee = new Employee();
        employee.setId(42L);
        user.setEmployee(employee);
        user.setUserName("testuser");

        // Act
        UserDTO userDTO = mapper.toDto(user);

        // Assert
        assertNotNull(userDTO, "UserDTO should not be null");
        assertEquals(42L, userDTO.getEmployeeId(), "Employee ID should match the source User's Employee ID");
        assertEquals("testuser", userDTO.getUserName(), "Username should match the source User");
    }

    @Test
    public void testToDTOList() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        Employee employee1 = new Employee();
        employee1.setId(42L);
        user1.setEmployee(employee1);
        user1.setUserName("user1");

        User user2 = new User();
        user2.setId(2L);
        Employee employee2 = new Employee();
        employee2.setId(43L);
        user2.setEmployee(employee2);
        user2.setUserName("user2");

        List<User> users = List.of(user1, user2);

        // Act
        List<UserDTO> userDTOs = mapper.toDTOList(users);

        // Assert
        assertNotNull(userDTOs, "UserDTO list should not be null");
        assertEquals(2, userDTOs.size(), "UserDTO list size should match the source list size");

        UserDTO userDTO1 = userDTOs.get(0);
        assertEquals(42L, userDTO1.getEmployeeId(), "First UserDTO Employee ID should match");
        assertEquals("user1", userDTO1.getUserName(), "First UserDTO Username should match");

        UserDTO userDTO2 = userDTOs.get(1);
        assertEquals(43L, userDTO2.getEmployeeId(), "Second UserDTO Employee ID should match");
        assertEquals("user2", userDTO2.getUserName(), "Second UserDTO Username should match");
    }
    
    @Test
    public void testToDtoWithNullEmployee() {
        User user = new User();
        user.setId(1L);
        user.setEmployee(null);
        user.setUserName("testuser");
        
        UserDTO userDTO = mapper.toDto(user);
        
        assertNotNull(userDTO);
        assertNull(userDTO.getEmployeeId());
    }

    @Test
    public void testToDtoWithNullUser() {
        UserDTO userDTO = mapper.toDto(null);
        assertNull(userDTO);
    }
    
    @Test
    public void testUnmappedFieldsAreIgnored() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmployee(new Employee());
        user.setUserName("testuser");
        // Add some field to User that isn't in UserDTO
        
        // Act
        UserDTO userDTO = mapper.toDto(user);
        
        // Assert
        assertNotNull(userDTO);
        // Test should pass without any exceptions about unmapped fields
    }
    
    @Test
    public void testNestedNullHandling() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Employee employee = new Employee();
        // Don't set employee.id
        user.setEmployee(employee);
        user.setUserName("testuser");
        
        // Act
        UserDTO userDTO = mapper.toDto(user);
        
        // Assert
        assertNotNull(userDTO);
        assertNull(userDTO.getEmployeeId());
    }
    
    @Test
    public void testEmptyList() {
        // Arrange
        List<User> emptyList = Collections.emptyList();
        
        // Act
        List<UserDTO> userDTOs = mapper.toDTOList(emptyList);
        
        // Assert
        assertNotNull(userDTOs, "Should return empty list, not null");
        assertTrue(userDTOs.isEmpty(), "Result list should be empty");
    }
}
