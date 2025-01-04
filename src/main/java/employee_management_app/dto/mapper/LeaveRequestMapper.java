package employee_management_app.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.model.LeaveRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveRequestMapper {
// TODO create LeaveRequestMapper
	@Mapping(target = "employeeId", ignore = true)
    LeaveRequestDTO toDTO(LeaveRequest leaveRequest);
    
    @Mapping(target = "employee.id", source = "employeeId")
    LeaveRequest toEntity(LeaveRequestDTO leaveRequestDTO);

}
