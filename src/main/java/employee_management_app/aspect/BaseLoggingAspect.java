package employee_management_app.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseLoggingAspect {
    
    protected Logger log;
    
    public BaseLoggingAspect(Class<?> clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }
    
    protected Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        log.info("Started executing {}.{} with parameters: {}", 
                className, methodName, joinPoint.getArgs());
        
        try {
            Object result = joinPoint.proceed();
            log.info("Successfully completed {}.{}", className, methodName);
            return result;
        } catch (Exception e) {
            log.error("Exception in {}.{}: {}", className, methodName, e.getMessage());
            throw e;
        }
    }
    
    protected void logMethodEntrance(String methodName, Object... args) {
        log.info("Entering method {} with arguments: {}", methodName, args);
    }
    
    protected void logMethodExit(String methodName, Object result) {
        log.info("Exiting method {} with result: {}", methodName, result);
    }
    
    protected void logException(String methodName, Exception e) {
        log.error("Exception in method {}: {}", methodName, e.getMessage());
    }
}