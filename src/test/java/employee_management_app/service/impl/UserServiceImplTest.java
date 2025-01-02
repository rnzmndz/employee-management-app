package employee_management_app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import employee_management_app.dto.mapper.user.UserCreateMapper;
import employee_management_app.dto.mapper.user.UserMapper;
import employee_management_app.dto.mapper.user.UserUpdateMapper;
import employee_management_app.dto.user.UserCreateDTO;
import employee_management_app.dto.user.UserDTO;
import employee_management_app.dto.user.UserUpdateDTO;
import employee_management_app.exception.UserNotFoundException;
import employee_management_app.exception.UsernameAlreadyExistsException;
import employee_management_app.model.Employee;
import employee_management_app.model.User;
import employee_management_app.model.enums.UserRole;
import employee_management_app.repository.EmployeeRepository;
import employee_management_app.repository.UserRepository;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserCreateMapper createMapper;

    @Mock
    private UserUpdateMapper updateMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserDTO testUserDto;
    private UserCreateDTO testCreateDto;
    private UserUpdateDTO testUpdateDto;
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        testUser = new User();
        testUser.setId(1L);
        testUser.setUserName("testUser");
        testUser.setRole(UserRole.ADMIN);
        
        testEmployee = new Employee();
        testEmployee.setId(1L);

        testUserDto = new UserDTO();
        testUserDto.setId(1L);
        testUserDto.setUserName("testUser");

        testCreateDto = new UserCreateDTO();
        testCreateDto.setUserName("newUser");
        testCreateDto.setEmployeeId(1L);

        testUpdateDto = new UserUpdateDTO();
        testUpdateDto.setUserName("updatedUser");
    }

    @Test
    void createUser_Success() {
        // Arrange
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(testEmployee));
        when(createMapper.toEntity(any(UserCreateDTO.class))).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        UserCreateDTO result = userService.createUser(testCreateDto);

        // Assert
        assertNotNull(result);
        verify(userRepository).existsByUserName(testCreateDto.getUserName());
        verify(employeeRepository).findById(testCreateDto.getEmployeeId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_UsernameAlreadyExists() {
        // Arrange
        when(userRepository.existsByUserName(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> 
            userService.createUser(testCreateDto)
        );
    }

    @Test
    void createUser_NullDTO() {
        assertThrows(IllegalArgumentException.class, () -> 
            userService.createUser(null)
        );
    }

    @Test
    void findByUsername_Success() {
        // Arrange
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(any(User.class))).thenReturn(testUserDto);

        // Act
        Optional<UserDTO> result = userService.findByUsername("testUser");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUserName());
    }

    @Test
    void findByUsername_NotFound() {
        // Arrange
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<UserDTO> result = userService.findByUsername("nonexistent");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void updateUser_Success() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        UserUpdateDTO result = userService.updateUser(1L, testUpdateDto);

        // Assert
        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_NotFound() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> 
            userService.updateUser(1L, testUpdateDto)
        );
    }

    @Test
    void deleteUser_Success() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).deleteById(anyLong());

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_NotFound() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> 
            userService.deleteUser(1L)
        );
    }

    @Test
    void getAllUsers_Success() {
        // Arrange
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(any(User.class))).thenReturn(testUserDto);

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void setDefaultPermissions_AdminRole() {
        // Arrange
        User user = new User();
        user.setRole(UserRole.ADMIN);

        // Act
        userService.setDefaultPermissions(user);

        // Assert
        Set<String> expectedPermissions = new HashSet<>(Arrays.asList(
            "USER_CREATE", "USER_READ", "USER_UPDATE", "USER_DELETE",
            "EMPLOYEE_CREATE", "EMPLOYEE_READ", "EMPLOYEE_UPDATE", "EMPLOYEE_DELETE"
        ));
        assertEquals(expectedPermissions, user.getPermissions());
    }

    @Test
    void setDefaultPermissions_ManagerRole() {
        // Arrange
        User user = new User();
        user.setRole(UserRole.MANAGER);

        // Act
        userService.setDefaultPermissions(user);

        // Assert
        Set<String> expectedPermissions = new HashSet<>(Arrays.asList(
            "EMPLOYEE_READ", "EMPLOYEE_UPDATE", "LEAVE_APPROVE", "ATTENDANCE_VIEW"
        ));
        assertEquals(expectedPermissions, user.getPermissions());
    }
}