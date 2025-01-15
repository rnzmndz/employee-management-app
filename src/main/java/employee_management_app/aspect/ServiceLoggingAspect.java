package employee_management_app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import employee_management_app.model.enums.EmployeeStatus;

@Aspect
@Component
public class ServiceLoggingAspect extends BaseLoggingAspect {
    
    public ServiceLoggingAspect() {
        super(ServiceLoggingAspect.class);
    }
    
    @Around("execution(* employee_management_app.service.impl.*ServiceImpl.*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint);
    }
    
    @Around("execution(* employee_management_app.service.impl.*ServiceImpl.create*(..))")
    public Object logCreateEmployee(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Starting employee creation process");
        Object result = logMethodExecution(joinPoint);
        log.info("Employee creation process completed");
        return result;
    }
    
    @Around("execution(* employee_management_app.service.impl.*ServiceImpl.updateEmployeeStatus(..))")
    public Object logStatusUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Long employeeId = (Long) joinPoint.getArgs()[0];
        EmployeeStatus newStatus = (EmployeeStatus) joinPoint.getArgs()[1];
        log.info("Updating status for employee ID: {} to: {}", employeeId, newStatus);
        Object result = logMethodExecution(joinPoint);
        log.info("Status update completed for employee ID: {}", employeeId);
        return result;
    }
    
    @Around("execution(* employee_management_app.service.impl.*ServiceImpl.transferDepartment(..))")
    public Object logDepartmentTransfer(ProceedingJoinPoint joinPoint) throws Throwable {
        Long employeeId = (Long) joinPoint.getArgs()[0];
        Long departmentId = (Long) joinPoint.getArgs()[1];
        log.info("Transferring employee ID: {} to department ID: {}", employeeId, departmentId);
        Object result = logMethodExecution(joinPoint);
        log.info("Department transfer completed for employee ID: {}", employeeId);
        return result;
    }
    
    @Around("execution(* employee_management_app.service.impl.*ServiceImpl.findEmployeesHiredBetween(..))")
    public Object logHiredBetweenSearch(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Searching for employees hired between {} and {}", 
                joinPoint.getArgs()[0], joinPoint.getArgs()[1]);
        return logMethodExecution(joinPoint);
    }
}