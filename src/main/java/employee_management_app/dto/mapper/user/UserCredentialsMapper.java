package employee_management_app.dto.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.user.UserCredentialsDTO;
import employee_management_app.model.AppUser;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserCredentialsMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	AppUser toEntity(UserCredentialsDTO userCredentialsDTO);
	
	UserCredentialsDTO toDto(AppUser appUser);
}
