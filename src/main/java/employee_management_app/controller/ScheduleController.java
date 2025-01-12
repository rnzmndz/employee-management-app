package employee_management_app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.service.ScheduleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<ScheduleDTO> assignSchedule(
            @PathVariable Long employeeId,
            @Valid @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.assignSchedule(employeeId, scheduleDTO));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ScheduleDTO>> getEmployeeSchedules(
            @PathVariable Long employeeId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.getEmployeeSchedules(employeeId, startDate, endDate));
    }

    @GetMapping("/check-conflict")
    public ResponseEntity<Boolean> hasScheduleConflict(
            @RequestParam Long employeeId,
            @Valid @RequestBody ScheduleDTO newSchedule) {
        return ResponseEntity.ok(scheduleService.hasScheduleConflict(employeeId, newSchedule));
    }
}