package employee_management_app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect extends BaseLoggingAspect {
    
    public ControllerLoggingAspect() {
        super(ControllerLoggingAspect.class);
    }
    
    @Around("execution(* employee_management_app.controller.*Controller.*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint);
    }
    
    @Around("execution(* employee_management_app.controller.*Controller.create* (..))")
    public Object logCreateEmployee(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Attempting to create new employee");
        Object result = logMethodExecution(joinPoint);
        log.info("Successfully created new employee");
        return result;
    }
    
    @Around("execution(* employee_management_app.controller.*Controller.delete*(..))")
    public Object logDeleteEmployee(ProceedingJoinPoint joinPoint) throws Throwable {
        Long employeeId = (Long) joinPoint.getArgs()[0];
        log.info("Attempting to delete employee with ID: {}", employeeId);
        Object result = logMethodExecution(joinPoint);
        log.info("Successfully deleted employee with ID: {}", employeeId);
        return result;
    }
    
    @Around("execution(* employee_management_app.controller.*Controller.update*(..))")
    public Object logUpdateEmployee(ProceedingJoinPoint joinPoint) throws Throwable {
        Long employeeId = (Long) joinPoint.getArgs()[0];
        log.info("Attempting to update employee with ID: {}", employeeId);
        Object result = logMethodExecution(joinPoint);
        log.info("Successfully updated employee with ID: {}", employeeId);
        return result;
    }
}