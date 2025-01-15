package employee_management_app.dto.mapper.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import employee_management_app.dto.user.UserUpdateDTO;
import employee_management_app.model.Employee;
import employee_management_app.model.AppUser;

class UserUpdateMapperTest {

    private final UserUpdateMapper mapper = Mappers.getMapper(UserUpdateMapper.class);

    @Test
    public void testUpdateEntityFromDto() {
        // Arrange
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmployeeId(42L);
        updateDTO.setUsername("updatedUser");

        AppUser user = new AppUser();
        user.setId(1L);
        user.setPassword("originalPassword");
        user.setCreatedAt(LocalDateTime.now());
        Employee employee = new Employee();
        employee.setId(1L);
        user.setEmployee(employee);

        // Act
        mapper.updateEntityFromDto(updateDTO, user);

        // Assert
        assertNotNull(user, "User should not be null");
        assertEquals(1L, user.getId(), "ID should remain unchanged");
        assertEquals("updatedUser", user.getUsername(), "Username should be updated");
        assertEquals(42L, user.getEmployee().getId(), "Employee ID should be updated from DTO");
        assertNotNull(user.getPassword(), "Password should remain unchanged");
        assertNotNull(user.getCreatedAt(), "CreatedAt should remain unchanged");
    }
}
