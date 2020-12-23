package sustech.edu.phantom.dboj.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Aspect
public class WebAspectLog {

    @Pointcut("execution(public * sustech.edu.phantom.dboj.controller..*.*(..))")
    public void Pointcut() {
    }

    @Pointcut("execution(public * sustech.edu.phantom.dboj.controller.UserController.polling())")
    public void PointcutExcept(){
    }
    @Pointcut("Pointcut() && !PointcutExcept()")
    public void PointcutN() {
    }

//    @Before("Pointcut()")
//    public void before(JoinPoint joinPoint) throws InterruptedException {
//        System.out.println(joinPoint.getSignature().getName());
//        Thread.sleep(2000L);
//    }

    @Around("PointcutN()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        Map<String, Object> data = new HashMap<>();
        //获取目标类名称
        String clazzName = pjp.getTarget().getClass().getName();
        //获取目标类方法名称
        String methodName = pjp.getSignature().getName();

        //记录类名称
        data.put("clazzName", clazzName);
        //记录对应方法名称
        data.put("methodName", methodName);
        //记录请求参数
//        data.put("params", pjp.getArgs());
        //开始调用时间
        // 计时并调用目标函数
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        Long time = System.currentTimeMillis() - start;

        //记录返回参数
//        data.put("result", result);

        //设置消耗总时间
        data.put("consumeTime", time);
        log.info(data.toString());

        return result;
    }
}
