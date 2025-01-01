package employee_management_app.service;

import java.util.List;
import java.util.Optional;

import employee_management_app.dto.user.UserCreateDTO;
import employee_management_app.dto.user.UserDTO;
import employee_management_app.dto.user.UserUpdateDTO;

public interface UserService {
    /**
     * Create a new user.
     *
     * @param dto the user data to create
     * @return the created user
     * @throws UserAlreadyExistsException if username already exists
     * @throws ValidationException if user data is invalid
     * @since 1.0
     */
    UserCreateDTO createUser(UserCreateDTO dto);

    /**
     * Find a user by their username.
     *
     * @param username the username to search for (case-sensitive)
     * @return an Optional containing the user if found, or empty if not
     * @throws IllegalArgumentException if username is null or empty
     * @since 1.0
     */
    Optional<UserDTO> findByUsername(String username);

    /**
     * Find a user by their ID.
     *
     * @param id the user ID
     * @return an Optional containing the user if found, or empty if not
     * @throws IllegalArgumentException if id is null
     * @since 1.0
     */
    Optional<UserDTO> findById(Long id);

    /**
     * Update an existing user's details.
     *
     * @param id the user ID
     * @param updatedUser the updated user details
     * @return the updated user
     * @throws UserNotFoundException if user doesn't exist
     * @throws ValidationException if updated data is invalid
     * @throws DuplicateUsernameException if new username conflicts with existing user
     * @since 1.0
     */
    UserUpdateDTO updateUser(Long id, UserUpdateDTO updatedUser);

    /**
     * Delete a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws UserNotFoundException if user doesn't exist
     * @since 1.0
     */
    void deleteUser(Long id);

    /**
     * Get a list of all users.
     *
     * @return a list of all users, empty list if no users exist
     * @since 1.0
     */
    List<UserDTO> getAllUsers();
    /**
     * Retrieve a paginated list of all users.
     *
     * @param pageable the pagination and sorting information
     * @return a Page of UserDTO objects containing user details
     * @throws IllegalArgumentException if the pageable object is invalid or null
     */
//    Page<UserDTO> getAllUsers(Pageable pageable);

    /**
     * Search for users based on specific criteria.
     *
     * @param criteria the criteria for filtering users
     * @return a List of UserDTO objects matching the criteria
     * @throws IllegalArgumentException if the criteria object is null
     * @throws RuntimeException if an unexpected error occurs during the search operation
     */
//    List<UserDTO> searchUsers(UserSearchCriteria criteria);
}
