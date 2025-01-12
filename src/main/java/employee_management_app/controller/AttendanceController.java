package employee_management_app.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.service.AttendanceService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<Void> recordAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        attendanceService.recordAttendance(attendanceDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<AttendanceDTO>> getAttendanceByDateRange(
            @PathVariable Long employeeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            Pageable pageable) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDateRange(employeeId, startDate, endDate, pageable));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getAttendanceStatistics(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(attendanceService.getAttendanceStatistics(startDate, endDate));
    }
}