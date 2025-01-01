package employee_management_app.aspect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import employee_management_app.dto.user.UserDTO;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class UserServiceAspect {

	/**
	 * Pointcut that matches all methods in UserService implementation
	 */
	@Pointcut("execution(* employee_management_app.service.UserServiceImpl.*(..))")
	public void userServiceMethods() {}
	
	/**
	 * Log method entry with parameters
	 */
	@Before("userServiceMethods()")
	public void logMethodEntry(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		log.info("Entering method: {} with parameters: {}", methodName, Arrays.toString(args));
	}
	
	/**
	 * Handle and log exceptions
	 */
	@Around("userServiceMethods()")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		
		String methodName = joinPoint.getSignature().getName();
		log.info("Method {} executed in {} ms", methodName, (endTime - startTime));
		
		return result;
	}
	
	@Before("execution(* employee_management_app.service.UserServiceImpl.createUser(..)) || "
			+ "\"execution(* employee_management_app.service.UserServiceImpl.updateUser(..))")
	public void validateUserInput(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof UserDTO) {
				UserDTO userDTO = (UserDTO) arg;
				validateUserDTO(userDTO);
			}
		}
	}
	
	@AfterReturning(pointcut = "execution(* employee_management_app.service.UserServiceImpl.createUser(..)) || "
			+ "\"execution(* employee_management_app.service.UserServiceImpl.updateUser(..))",
			returning = "result")
	public void auditOperation(JoinPoint joinPoint, Object result) {
		if (result instanceof UserDTO) {
			UserDTO userDTO = (UserDTO) result;
			auditLog(joinPoint.getSignature().getName(), userDTO.getEmployeeId());
		}
	}
	
	private void validateUserDTO(UserDTO userDTO) {
		List<String> violations = new ArrayList<>();
		
		if(userDTO.getUserName() == null || userDTO.getUserName().trim().isEmpty()) {
			violations.add("Username cannot be empty");
		}
		
		if (userDTO.getEmployeeId() == null) {
			violations.add("Employee ID cannot be null");
		}
		
		if (!violations.isEmpty()) {
			throw new ValidationException("Validation failed: " + String.join(", ", violations));
		}
	}
	
	private void auditLog(String operation, Long userId) {
//		Get current authenticated user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String performer = auth != null ? auth.getName() : "SYSTEM";
		
		log.info("AUDIT: {} performed {} on user ID: {}",
				performer, operation, userId);
	}
}
