package top.juntech.community.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author juntech
 * @version ${version}
 * @date 2020/6/1 23:29
 * @ClassName 类名
 * @Descripe 日志
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* top.juntech.community.controller.IndexController.*(..))")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void  beforeLog(JoinPoint jp){
        log.info("调用 " + jp.getTarget() + " 的 " + jp.getSignature().getName()
                + " 方法。方法入参：" + Arrays.toString(jp.getArgs()));
    }

    @Around("pointCut()")
    public void aroundLog(ProceedingJoinPoint jp){
        long start_time = System.currentTimeMillis();
        log.info("调用 " + jp.getTarget() + " 的 " + jp.getSignature().getName()
                + " 方法。方法入参：" + Arrays.toString(jp.getArgs()));
        try {
            Object result = jp.proceed();
            log.info("调用 " + jp.getTarget() + " 的 "
                    + jp.getSignature().getName() + " 方法。方法返回值：" + result);

        } catch (Throwable e) {
            log.error(jp.getSignature().getName() + " 方法发生异常：" + e);
            try {
                throw e;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } finally {
            log.info(jp.getSignature().getName() + " 方法结束执行。");
        }
        long end_time = System.currentTimeMillis();
        log.info("执行完程序共花费了：{}ms",(end_time-start_time));
    }

}
