package top.juntech.community.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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
    public void  before(JoinPoint jp){
        log.info("调用 " + jp.getTarget() + " 的 " + jp.getSignature().getName()
                             + " 方法。方法入参：" + Arrays.toString(jp.getArgs()));
    }

    @After("pointCut()")
    public void  after(JoinPoint jp){
        log.info("调用 " + jp.getTarget() + " 的 " + jp.getSignature().getName()
                             + " 方法,调用结束");
    }



}
