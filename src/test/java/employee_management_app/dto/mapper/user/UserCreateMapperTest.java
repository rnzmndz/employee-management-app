package employee_management_app.dto.mapper.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import employee_management_app.dto.user.UserCreateDTO;
import employee_management_app.model.AppUser;
import employee_management_app.model.enums.UserStatus;

class UserCreateMapperTest {

    private final UserCreateMapper mapper = Mappers.getMapper(UserCreateMapper.class);

    @Test
    public void testToEntity() {
        // Arrange
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmployeeId(42L);
        userCreateDTO.setUserName("testuser");
        
        // Act
        AppUser user = mapper.toEntity(userCreateDTO);

        // Assert
        assertNotNull(user, "User should not be null");
        assertNull(user.getId(), "ID should be ignored and null");
        assertNotNull(user.getEmployee(), "Employee should not be null");
        assertEquals(42L, user.getEmployee().getId(), "Employee ID should match the source DTO");
        assertNull(user.getCreatedAt(), "CreatedAt should be ignored and null");
        assertNull(user.getUpdatedAt(), "UpdatedAt should be ignored and null");
        assertEquals(UserStatus.ACTIVE, user.getStatus(), "Status should be set to ACTIVE");
        assertTrue(user.isAccountNonLocked(), "AccountNonLocked should be true");
        assertEquals(0, user.getFailedAttempt(), "FailedAttempt should be 0");
    }
}