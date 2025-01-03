package employee_management_app.dto.mapper.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.user.UserCreateDTO;
import employee_management_app.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserCreateMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "employee.id", source = "employeeId")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "status", expression = "java(employee_management_app.model.enums.UserStatus.ACTIVE)")
	User toEntity(UserCreateDTO userCreateDTO);
	
	@InheritInverseConfiguration
    @Mapping(target = "employeeId", source = "employee.id")
    UserCreateDTO toDTO(User user);
}
