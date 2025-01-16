package employee_management_app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import employee_management_app.dto.user.UserCreateDTO;
import employee_management_app.dto.user.UserCredentialsDTO;
import employee_management_app.dto.user.UserDTO;
import employee_management_app.dto.user.UserUpdateDTO;
import employee_management_app.exception.UserNotFoundException;
import employee_management_app.exception.UsernameAlreadyExistsException;
import employee_management_app.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

/**
 * REST controller for managing users.
 * 
 * Provides endpoints to create, retrieve, update, and delete users, as well as to fetch users by username.
 * All operations are mapped under the base URL `/api/users`.
 */


@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user.
     * 
     * @param userDTO the user data to create
     * @return ResponseEntity containing the created user and HTTP status 201 (Created)
     * @throws ResponseStatusException if the username already exists (HTTP 409 Conflict)
     *                                or a related entity is not found (HTTP 404 Not Found)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserCreateDTO> createUser(@Valid @RequestBody UserCreateDTO userDTO) {
        try {
            UserCreateDTO createdUser = userService.createUser(userDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return ResponseEntity containing the user and HTTP status 200 (OK)
     * @throws ResponseStatusException if the user is not found (HTTP 404 Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        // Get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Get requested user
        Optional<UserCredentialsDTO> userCredential = userService.findById(id);
        
        // Check if userCredential is present
        if (userCredential.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Check if the authenticated user is requesting their own data
        if (!userCredential.get().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        return userService.findByUsername(username)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return ResponseEntity containing the user and HTTP status 200 (OK)
     * @throws ResponseStatusException if the user is not found (HTTP 404 Not Found)
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Retrieves all the user.
     *
     * @return ResponseEntity containing the list of user and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Updates a user by their ID.
     *
     * @param id the ID of the user
     * @param userDTO contains the updated data for user
     * @return ResponseEntity containing the updated user and HTTP status 200 (OK)
     * @throws ResponseStatusException if the user is not found (HTTP 404 Not Found)
     * @throws ResponseStatusException if the username is already exist (HTTP 409 Conflict)
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userDTO) {
        try {
            UserUpdateDTO updatedUser = userService.updateUser(id, userDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UsernameAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    /**
     * Delete a user by their ID.
     *
     * @param id the ID of the user
     * @throws ResponseStatusException if the user is not found (HTTP 404 Not Found)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}