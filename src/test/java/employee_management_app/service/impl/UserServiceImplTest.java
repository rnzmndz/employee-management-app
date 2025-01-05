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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
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

    private UserCreateDTO createDTO;
    private UserUpdateDTO updateDTO;
    private User user;
    private UserDTO userDTO;
    private Employee employee;

    @BeforeEach
    void setUp() {
        createDTO = new UserCreateDTO();
        createDTO.setUserName("testUser");
        createDTO.setEmployeeId(1L);

        updateDTO = new UserUpdateDTO();
        updateDTO.setUserName("updatedUser");

        employee = new Employee();
        employee.setId(1L);

        user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setEmployee(employee);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUserName("testUser");
    }

    @Test
    void createUser_Success() {
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(createMapper.toEntity(any(UserCreateDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        user.setRole(UserRole.ADMIN);

        UserCreateDTO result = userService.createUser(createDTO);

        assertNotNull(result);
        assertEquals(createDTO.getUserName(), result.getUserName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_UsernameExists_ThrowsException() {
        when(userRepository.existsByUserName(anyString())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> 
            userService.createUser(createDTO)
        );
    }

    @Test
    void createUser_NullUsername_ThrowsException() {
        createDTO.setUserName(null);

        assertThrows(IllegalArgumentException.class, () -> 
            userService.createUser(createDTO)
        );
    }

    @Test
    void createUser_EmployeeNotFound_ThrowsException() {
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> 
            userService.createUser(createDTO)
        );
    }

    @Test
    void findByUsername_Success() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        Optional<UserDTO> result = userService.findByUsername("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUserName());
    }

    @Test
    void findByUsername_NotFound() {
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.findByUsername("nonexistent");

        assertFalse(result.isPresent());
    }

    @Test
    void updateUser_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserUpdateDTO result = userService.updateUser(1L, updateDTO);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_NotFound_ThrowsException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> 
            userService.updateUser(1L, updateDTO)
        );
    }

    @Test
    void updateUser_DuplicateUsername_ThrowsException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.existsByUserName(anyString())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class, () -> 
            userService.updateUser(1L, updateDTO)
        );
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_NotFound_ThrowsException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> 
            userService.deleteUser(1L)
        );
    }

    @Test
    void getAllUsers_Success() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void setDefaultPermissions_Admin() {
        user.setRole(UserRole.ADMIN);
        userService.setDefaultPermissions(user);

        Set<String> permissions = user.getPermissions();
        assertTrue(permissions.contains("USER_CREATE"));
        assertTrue(permissions.contains("USER_READ"));
        assertTrue(permissions.contains("USER_UPDATE"));
        assertTrue(permissions.contains("USER_DELETE"));
        assertTrue(permissions.contains("EMPLOYEE_CREATE"));
        assertTrue(permissions.contains("EMPLOYEE_READ"));
        assertTrue(permissions.contains("EMPLOYEE_UPDATE"));
        assertTrue(permissions.contains("EMPLOYEE_DELETE"));
    }

    @Test
    void setDefaultPermissions_Manager() {
        user.setRole(UserRole.MANAGER);
        userService.setDefaultPermissions(user);

        Set<String> permissions = user.getPermissions();
        assertTrue(permissions.contains("EMPLOYEE_READ"));
        assertTrue(permissions.contains("EMPLOYEE_UPDATE"));
        assertTrue(permissions.contains("LEAVE_APPROVE"));
        assertTrue(permissions.contains("ATTENDANCE_VIEW"));
    }

    @Test
    void setDefaultPermissions_NullRole_ThrowsException() {
        user.setRole(null);
        
        assertThrows(IllegalArgumentException.class, () -> 
            userService.setDefaultPermissions(user)
        );
    }
}