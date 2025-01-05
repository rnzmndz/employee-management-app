package employee_management_app.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.model.LeaveRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeaveRequestMapper {
// TODO create LeaveRequestMapper
    LeaveRequestDTO toDTO(LeaveRequest leaveRequest);
    
    @Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee.id", source = "requestedByEmployeeId")
    @Mapping(target = "approvedBy.id", source = "approvedByEmployeeId")
    LeaveRequest toEntity(LeaveRequestDTO leaveRequestDTO);

}
