package employee_management_app.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.dto.employee.EmployeeCreateDTO;
import employee_management_app.dto.employee.EmployeeDTO;
import employee_management_app.dto.employee.EmployeeDetailDTO;
import employee_management_app.dto.employee.EmployeeListDTO;
import employee_management_app.dto.employee.EmployeeUpdateDTO;
import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.dto.schedule.ScheduleDTO;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    private EmployeeCreateDTO createEmployeeDTO;
    private EmployeeUpdateDTO updateEmployeeDTO;
    private EmployeeDTO employeeDTO;
    private EmployeeDetailDTO employeeDetailDTO;
    private AttendanceDTO attendanceDTO;
    private LeaveRequestDTO leaveRequestDTO;
    private ScheduleDTO scheduleDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        // Initialize test data
        createEmployeeDTO = new EmployeeCreateDTO();
        updateEmployeeDTO = new EmployeeUpdateDTO();
        employeeDTO = new EmployeeDTO();
        employeeDetailDTO = new EmployeeDetailDTO();
        attendanceDTO = new AttendanceDTO();
        leaveRequestDTO = new LeaveRequestDTO();
        scheduleDTO = new ScheduleDTO();
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void createEmployee_Success() {
        when(employeeService.createEmployee(any(EmployeeCreateDTO.class)))
            .thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.createEmployee(createEmployeeDTO);

        assertNotNull(result);
        verify(employeeService).createEmployee(createEmployeeDTO);
    }

    @Test
    void createEmployee_WithNullDTO_ThrowsException() {
        when(employeeService.createEmployee(null))
            .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> 
            employeeService.createEmployee(null));
    }

    @Test
    void updateEmployee_Success() {
        when(employeeService.updateEmployee(anyLong(), any(EmployeeUpdateDTO.class)))
            .thenReturn(employeeDetailDTO);

        EmployeeDetailDTO result = employeeService.updateEmployee(1L, updateEmployeeDTO);

        assertNotNull(result);
        verify(employeeService).updateEmployee(1L, updateEmployeeDTO);
    }

    @Test
    void getEmployeeById_Success() {
        when(employeeService.getEmployeeById(anyLong()))
            .thenReturn(employeeDetailDTO);

        EmployeeDetailDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        verify(employeeService).getEmployeeById(1L);
    }

    @Test
    void getAllEmployees_Success() {
        List<EmployeeListDTO> employees = new ArrayList<>();
        Page<EmployeeListDTO> employeePage = new PageImpl<>(employees);

        when(employeeService.getAllEmployees(any(Pageable.class)))
            .thenReturn(employeePage);

        Page<EmployeeListDTO> result = employeeService.getAllEmployees(pageable);

        assertNotNull(result);
        verify(employeeService).getAllEmployees(pageable);
    }

    @Test
    void searchEmployees_Success() {
        String keyword = "John";
        List<EmployeeListDTO> employees = new ArrayList<>();
        Page<EmployeeListDTO> employeePage = new PageImpl<>(employees);

        when(employeeService.searchEmployees(anyString(), any(Pageable.class)))
            .thenReturn(employeePage);

        Page<EmployeeListDTO> result = employeeService.searchEmployees(keyword, pageable);

        assertNotNull(result);
        verify(employeeService).searchEmployees(keyword, pageable);
    }

    @Test
    void transferDepartment_Success() {
        when(employeeService.transferDepartment(anyLong(), anyLong()))
            .thenReturn(employeeDetailDTO);

        EmployeeDetailDTO result = employeeService.transferDepartment(1L, 2L);

        assertNotNull(result);
        verify(employeeService).transferDepartment(1L, 2L);
    }

    @Test
    void recordAttendance_Success() {
        doNothing().when(employeeService).recordAttendance(anyLong(), any(AttendanceDTO.class));

        assertDoesNotThrow(() -> employeeService.recordAttendance(1L, attendanceDTO));
        verify(employeeService).recordAttendance(1L, attendanceDTO);
    }

    @Test
    void submitLeaveRequest_Success() {
        when(employeeService.submitLeaveRequest(anyLong(), any(LeaveRequestDTO.class)))
            .thenReturn(leaveRequestDTO);

        LeaveRequestDTO result = employeeService.submitLeaveRequest(1L, leaveRequestDTO);

        assertNotNull(result);
        verify(employeeService).submitLeaveRequest(1L, leaveRequestDTO);
    }

    @Test
    void assignSchedule_Success() {
        when(employeeService.assignSchedule(anyLong(), any(ScheduleDTO.class)))
            .thenReturn(scheduleDTO);

        ScheduleDTO result = employeeService.assignSchedule(1L, scheduleDTO);

        assertNotNull(result);
        verify(employeeService).assignSchedule(1L, scheduleDTO);
    }

    @Test
    void getEmployeeStatsByDepartment_Success() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("IT", 10);
        stats.put("HR", 5);

        when(employeeService.getEmployeeStatsByDepartment())
            .thenReturn(stats);

        Map<String, Integer> result = employeeService.getEmployeeStatsByDepartment();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeService).getEmployeeStatsByDepartment();
    }

    @Test
    void existsByEmail_Success() {
        String email = "test@example.com";
        when(employeeService.existsByEmail(anyString()))
            .thenReturn(true);

        boolean result = employeeService.existsByEmail(email);

        assertTrue(result);
        verify(employeeService).existsByEmail(email);
    }

    @Test
    void existsByPhone_Success() {
        String phone = "1234567890";
        when(employeeService.existsByPhone(anyString()))
            .thenReturn(false);

        boolean result = employeeService.existsByPhone(phone);

        assertFalse(result);
        verify(employeeService).existsByPhone(phone);
    }
}
