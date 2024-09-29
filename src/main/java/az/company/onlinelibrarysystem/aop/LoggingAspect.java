package az.company.onlinelibrarysystem.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Pointcut for all service methods
    @Pointcut("execution(* az.company.onlinelibrarysystem.service..*(..))")
    public void serviceMethods() {}

    // Log before method execution
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Executing method: " + methodName);
    }

    // Log after method execution
    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method executed: " + methodName);
    }

    // Log the method execution time
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        logger.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

    // Log exceptions if thrown
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in method: " + joinPoint.getSignature().getName() + " with message: " + error.getMessage());
    }
}
