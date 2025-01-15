package employee_management_app.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import employee_management_app.exception.ResourceNotFoundException;
import employee_management_app.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
@Disabled
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Nested
    @DisplayName("DELETE /api/employees/{id}")
    class DeleteEmployee {

        @Test
        @DisplayName("Should return 204 No Content when successfully deleting an employee")
        void shouldReturn204WhenDeletingExistingEmployee() throws Exception {
            // Arrange
            Long employeeId = 1L;
            doNothing().when(employeeService).deleteEmployee(employeeId);

            // Act & Assert
            mockMvc.perform(delete("/api/employees/{id}", employeeId)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());

            // Verify
            verify(employeeService, times(1)).deleteEmployee(employeeId);
        }

        @Test
        @DisplayName("Should return 404 Not Found when trying to delete non-existent employee")
        void shouldReturn404WhenDeletingNonExistentEmployee() throws Exception {
            // Arrange
            Long nonExistentId = 999L;
            doThrow(new ResourceNotFoundException("Employee not existing with ID: " + nonExistentId))
                .when(employeeService).deleteEmployee(nonExistentId);

            // Act & Assert
            mockMvc.perform(delete("/api/employees/{id}", nonExistentId)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Employee not existing with ID: " + nonExistentId));
        }

        @Test
        @DisplayName("Should return 400 Bad Request when trying to delete with invalid ID")
        void shouldReturn400WhenDeletingWithInvalidId() throws Exception {
            // Act & Assert
            mockMvc.perform(delete("/api/employees/invalid")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should handle internal server error during deletion")
        void shouldHandle500WhenInternalErrorOccurs() throws Exception {
            // Arrange
            Long employeeId = 1L;
            doThrow(new RuntimeException("Internal server error"))
                .when(employeeService).deleteEmployee(employeeId);

            // Act & Assert
            mockMvc.perform(delete("/api/employees/{id}", employeeId)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }

    // Global Exception Handler Test
    @Test
    @DisplayName("Should handle unexpected exceptions globally")
    void shouldHandleUnexpectedExceptionsGlobally() throws Exception {
        // Arrange
        Long employeeId = 1L;
        doThrow(new IllegalStateException("Unexpected error"))
            .when(employeeService).deleteEmployee(employeeId);

        // Act & Assert
        mockMvc.perform(delete("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists());
    }
}