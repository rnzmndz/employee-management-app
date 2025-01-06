package employee_management_app.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import employee_management_app.dto.department.DepartmentDTO;
import employee_management_app.model.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
	DepartmentDTO toDTO(Department department);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "employees", ignore =true)
	Department toEntity(DepartmentDTO departmentDTO);
}
