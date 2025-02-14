package employee_management_app.dto.mapper.user;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.user.UserDTO;
import employee_management_app.model.AppUser;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	@Mapping(target = "employeeId", source = "employee.id")
	UserDTO toDto(AppUser user);
	
	AppUser toEntity(UserDTO userDTO);
	
	List<UserDTO> toDTOList(List<AppUser> users);
}
