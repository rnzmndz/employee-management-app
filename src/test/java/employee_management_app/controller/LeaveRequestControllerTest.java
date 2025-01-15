package employee_management_app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.exception.ResourceNotFoundException;
import employee_management_app.model.enums.LeaveRequestStatus;
import employee_management_app.service.LeaveRequestService;

import java.util.Collections;

@WebMvcTest(LeaveRequestController.class)
@Disabled
public class LeaveRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LeaveRequestService leaveRequestService;

    @Nested
    @DisplayName("POST /api/leave-requests/employee/{employeeId}")
    class SubmitLeaveRequest {
        
        @Test
        @DisplayName("Should successfully submit leave request")
        void shouldSubmitLeaveRequest() throws Exception {
            // Arrange
            Long employeeId = 1L;
            LeaveRequestDTO requestDTO = new LeaveRequestDTO();
            when(leaveRequestService.submitLeaveRequest(eq(employeeId), any(LeaveRequestDTO.class)))
                .thenReturn(requestDTO);

            // Act & Assert
            mockMvc.perform(post("/api/leave-requests/employee/{employeeId}", employeeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 404 when employee not found")
        void shouldReturn404WhenEmployeeNotFound() throws Exception {
            // Arrange
            Long nonExistentId = 999L;
            LeaveRequestDTO requestDTO = new LeaveRequestDTO();
            when(leaveRequestService.submitLeaveRequest(eq(nonExistentId), any(LeaveRequestDTO.class)))
                .thenThrow(new ResourceNotFoundException("Employee not found"));

            // Act & Assert
            mockMvc.perform(post("/api/leave-requests/employee/{employeeId}", nonExistentId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("GET /api/leave-requests/employee/{employeeId}")
    class GetEmployeeLeaveRequests {

        @Test
        @DisplayName("Should return employee leave requests")
        void shouldReturnEmployeeLeaveRequests() throws Exception {
            // Arrange
            Long employeeId = 1L;
            Page<LeaveRequestDTO> page = new PageImpl<>(Collections.emptyList());
            when(leaveRequestService.getEmployeeLeaveRequests(eq(employeeId), any()))
                .thenReturn(page);

            // Act & Assert
            mockMvc.perform(get("/api/leave-requests/employee/{employeeId}", employeeId)
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("PUT /api/leave-requests/{requestId}/status")
    class UpdateLeaveRequestStatus {

        @Test
        @DisplayName("Should update leave request status")
        void shouldUpdateLeaveRequestStatus() throws Exception {
            // Arrange
            Long requestId = 1L;
            LeaveRequestDTO updatedDTO = new LeaveRequestDTO();
            when(leaveRequestService.updateLeaveRequestStatus(eq(requestId), any(LeaveRequestStatus.class)))
                .thenReturn(updatedDTO);

            // Act & Assert
            mockMvc.perform(put("/api/leave-requests/{requestId}/status", requestId)
                    .param("status", LeaveRequestStatus.APPROVED.toString()))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("GET /api/leave-requests/check-availability")
    class CheckLeaveAvailability {

        @Test
        @DisplayName("Should check leave availability")
        void shouldCheckLeaveAvailability() throws Exception {
            // Arrange
            Long employeeId = 1L;
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(5);
            when(leaveRequestService.isLeaveAllowed(employeeId, startDate, endDate))
                .thenReturn(true);

            // Act & Assert
            mockMvc.perform(get("/api/leave-requests/check-availability")
                    .param("employeeId", employeeId.toString())
                    .param("startDate", startDate.toString())
                    .param("endDate", endDate.toString()))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }
    }
}