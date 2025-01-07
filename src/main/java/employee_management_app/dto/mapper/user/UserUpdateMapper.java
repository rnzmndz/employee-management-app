package employee_management_app.dto.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.user.UserUpdateDTO;
import employee_management_app.model.AppUser;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserUpdateMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "employee.id", source = "employeeId")
	void updateEntityFromDto(UserUpdateDTO updateDTO, @MappingTarget AppUser user);
}
