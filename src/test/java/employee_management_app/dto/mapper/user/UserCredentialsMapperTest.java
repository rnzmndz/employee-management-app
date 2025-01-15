package employee_management_app.dto.mapper.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import employee_management_app.dto.user.UserCredentialsDTO;
import employee_management_app.model.AppUser;

class UserCredentialsMapperTest {

    private final UserCredentialsMapper mapper = Mappers.getMapper(UserCredentialsMapper.class);

    @Test
    public void testToEntity() {
        // Arrange
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setUsername("testuser");
        userCredentialsDTO.setPassword("password123");

        // Act
        AppUser user = mapper.toEntity(userCredentialsDTO);

        // Assert
        assertNotNull(user, "User should not be null");
        assertNull(user.getId(), "ID should be ignored and null");
        assertEquals("testuser", user.getUsername(), "Username should match the source DTO");
        assertEquals("password123", user.getPassword(), "Password should match the source DTO");
        assertNull(user.getCreatedAt(), "CreatedAt should be ignored and null");
        assertNull(user.getUpdatedAt(), "UpdatedAt should be ignored and null");
    }
}