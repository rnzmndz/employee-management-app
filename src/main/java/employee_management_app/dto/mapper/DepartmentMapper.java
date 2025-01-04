package employee_management_app.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.department.DepartmentDTO;
import employee_management_app.model.Department;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {

//	TODO create DepartmentMapper
	DepartmentDTO toDTO(Department department);
	
	@Mapping(target = "employees", ignore = true)
	Department toEntity(DepartmentDTO departmentDTO);
}
