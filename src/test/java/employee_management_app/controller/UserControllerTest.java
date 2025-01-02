package employee_management_app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import employee_management_app.dto.user.UserCreateDTO;
import employee_management_app.exception.GlobalExceptionHandler;
import employee_management_app.exception.UsernameAlreadyExistsException;
import employee_management_app.model.enums.UserRole;
import employee_management_app.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser_Success() throws Exception {
        Set<String> permissions = new HashSet<>(Arrays.asList("READ", "WRITE"));
        
        UserCreateDTO inputDto = UserCreateDTO.builder()
                .userName("testuser")
                .password("password123")
                .role(UserRole.ADMIN)
                .employeeId(1L)
                .permissions(permissions)
                .build();

        UserCreateDTO createdDto = UserCreateDTO.builder()
                .userName("testuser")
                .password("password123")
                .role(UserRole.ADMIN)
                .employeeId(1L)
                .permissions(permissions)
                .build();

        when(userService.createUser(any(UserCreateDTO.class))).thenReturn(createdDto);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("testuser"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.permissions").isArray())
        		.andExpect(jsonPath("$.permissions.length()").value(2));
    }

    @Test
    void createUser_ValidationFailure() throws Exception {
        UserCreateDTO inputDto = UserCreateDTO.builder()
                .userName("te")  // too short
                .password("123") // too short
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.password").exists());
    }

    @Test
    void createUser_UsernameAlreadyExists() throws Exception {
        UserCreateDTO inputDto = UserCreateDTO.builder()
                .userName("existinguser")
                .password("password123")
                .role(UserRole.ADMIN)
                .employeeId(1L)
                .build();

        when(userService.createUser(any(UserCreateDTO.class)))
                .thenThrow(new UsernameAlreadyExistsException("Username already exists"));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isConflict());
    }

    // Other test methods remain similar but updated to match the DTO structure
}
