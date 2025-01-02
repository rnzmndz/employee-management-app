package employee_management_app.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import employee_management_app.repository.EmployeeRepository;
import employee_management_app.repository.UserRepository;
import employee_management_app.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCreateMapper createMapper;
    @Autowired
    private UserUpdateMapper updateMapper;
//    @Autowired
//    private UserCredentialsMapper credentialsMapper;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserCreateDTO createUser(UserCreateDTO dto) {
//		Validate dto
		if (dto == null) {
			throw new IllegalArgumentException("UserDTO cannot be null");
		}

//		Validate if username is unique
		if (userRepository.existsByUserName(dto.getUserName())) {
			throw new UsernameAlreadyExistsException("Username already taken");
		}

//		Username validation should check for null first
		if (dto.getUserName() == null || dto.getUserName().trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty");
		}

//		Find employee
		Employee employee = employeeRepository.findById(dto.getEmployeeId())
				.orElseThrow(() -> new EntityNotFoundException("Employee not found"));

//		Create user
		User user = createMapper.toEntity(dto);
		user.setEmployee(employee);

//		Set default permission based on role
		setDefaultPermissions(user);

//		Set CreatedAt Time and date when it created
		user.setCreatedAt(LocalDateTime.now());
		
//		Save the user in database
		user = userRepository.save(user);
		return dto;
	}

	@Override
	public Optional<UserDTO> findByUsername(String username) {
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("The username input is empty or null");
		}

		return userRepository.findByUserName(username).map(userMapper::toDto);
	}

	@Override
	public Optional<UserDTO> findById(Long id) {
//		if the id is null or empty it will throw an IllegalArgumentException
		if (id == null) {
			throw new IllegalArgumentException("The username input is empty or null");
		}

		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(userMapper.toDto(user.get()));
	}

	@Override
	public UserUpdateDTO updateUser(Long id, UserUpdateDTO updatedUser) {
//		Check if the parameter is null
		if (updatedUser == null) {
			throw new IllegalArgumentException("Updated user cannot be null");
		}
//		find the user, verify if it is existing.
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
//		Map only non-null fields from updatedUSer to existingUser
		if (updatedUser.getUserName() != null) {
//			Check if new username conflicts with other users
			if (!existingUser.getUserName().equals(updatedUser.getUserName())
					&& userRepository.existsByUserName(updatedUser.getUserName())) {
				throw new UsernameAlreadyExistsException("Username already taken");
			}
			existingUser.setUserName(updatedUser.getUserName());
		}
		
//		Update the entity from the data from DTO
		updateMapper.updateEntityFromDto(updatedUser, existingUser);

		existingUser = userRepository.save(existingUser);
		return (UserUpdateDTO) updatedUser;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not existing"));

		userRepository.deleteById(id);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
	}

	protected void setDefaultPermissions(User user) {
		if (user.getRole() == null) {
			throw new IllegalArgumentException("User role cannot be null");
		}

		Set<String> permissions = new HashSet<>();

		switch (user.getRole()) {
		case ADMIN -> permissions.addAll(Arrays.asList("USER_CREATE", "USER_READ", "USER_UPDATE", "USER_DELETE",
				"EMPLOYEE_CREATE", "EMPLOYEE_READ", "EMPLOYEE_UPDATE", "EMPLOYEE_DELETE"));
		case MANAGER ->
			permissions.addAll(Arrays.asList("EMPLOYEE_READ", "EMPLOYEE_UPDATE", "LEAVE_APPROVE", "ATTENDANCE_VIEW"));
		default -> {
		}
		}
		user.setPermissions(permissions);
	}
}
