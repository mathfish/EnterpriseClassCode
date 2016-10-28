package thompson.library.system.utilities;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingInterceptor {
    private static Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Pointcut("execution(* thompson.library.system..*.*(..))")
    private void anyMethodInMyPackage(){}

    @Pointcut("execution(* thompson.library.system..*Test*.*(..))")
    private void driverTestConfigMethods(){}

    @Around("anyMethodInMyPackage() && !driverTestConfigMethods()")
    public Object logMethodTime(ProceedingJoinPoint joinPoint){
        long timeBegin = System.currentTimeMillis();
        Object returnVal = null;
        try {
            returnVal = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            double timeInMethod = System.currentTimeMillis() - timeBegin;
            logger.debug("Time spent in {} : {} ms",joinPoint.getSignature().toShortString(), timeInMethod);
        }
        return returnVal;
    }
}
