package sustech.edu.phantom.dboj.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class WebAspectLog {

    @Pointcut("execution(public * sustech.edu.phantom.dboj.controller..*.*(..))")
    public void Pointcut(){}

    @AfterThrowing(value="Pointcut()",throwing="e")
    public void afterReturningMethod(JoinPoint joinPoint, NumberFormatException e){
        log.info("调用了异常通知 in aspect" + e.getMessage());
    }
}
