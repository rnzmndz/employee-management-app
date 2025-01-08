package employee_management_app.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.model.enums.LeaveRequestStatus;
import employee_management_app.service.LeaveRequestService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

//    @Autowired
//    private LeaveRequestService leaveRequestService;
//
//    @PostMapping("/employee/{employeeId}")
//    public ResponseEntity<LeaveRequestDTO> submitLeaveRequest(
//            @PathVariable Long employeeId,
//            @Valid @RequestBody LeaveRequestDTO leaveRequestDTO) {
//        return ResponseEntity.ok(leaveRequestService.submitLeaveRequest(employeeId, leaveRequestDTO));
//    }
//
//    @GetMapping("/employee/{employeeId}")
//    public ResponseEntity<Page<LeaveRequestDTO>> getEmployeeLeaveRequests(
//            @PathVariable Long employeeId,
//            Pageable pageable) {
//        return ResponseEntity.ok(leaveRequestService.getEmployeeLeaveRequests(employeeId, pageable));
//    }
//
//    @PutMapping("/{requestId}/status")
//    public ResponseEntity<LeaveRequestDTO> updateLeaveRequestStatus(
//            @PathVariable Long requestId,
//            @RequestParam LeaveRequestStatus status) {
//        return ResponseEntity.ok(leaveRequestService.updateLeaveRequestStatus(requestId, status));
//    }
//
//    @GetMapping("/check-availability")
//    public ResponseEntity<Boolean> isLeaveAllowed(
//            @RequestParam Long employeeId,
//            @RequestParam LocalDate startDate,
//            @RequestParam LocalDate endDate) {
//        return ResponseEntity.ok(leaveRequestService.isLeaveAllowed(employeeId, startDate, endDate));
//    }
}