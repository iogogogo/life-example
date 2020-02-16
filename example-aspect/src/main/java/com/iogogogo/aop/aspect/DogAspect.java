package com.iogogogo.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by tao.zeng on 2020/2/16.
 */
@Slf4j
@Aspect
@Configuration
public class DogAspect {

    @Pointcut("execution(public * com.iogogogo.aop.service.*.*(..))")
    public void pointcut() {
    }

    @Before(value = "pointcut()")
    public void before() {
        log.info("使用@Pointcut 的前置通知");
    }

    /**
     * 前置通知
     * <p>
     * 前置通知通过@Before注解进行标注，并可直接传入切点表达式的值，该通知在目标函数执行前执行。
     * 注意JoinPoint，是Spring提供的静态变量，通过joinPoint 参数，可以获取目标对象的信息,如类名称,方法参数,方法名称等，该参数是可选的。
     * <p>
     * ##
     *
     * @param joinPoint
     */
    @Before("execution(* com.iogogogo.aop.service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("前置通知 CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("前置通知 ARGS : {}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 后置通知
     * <p>
     * 通过@AfterReturning注解进行标注，该函数在目标函数执行完成后执行，并可以获取到目标函数最终的返回值returnVal。
     * 当目标函数没有返回值时，returnVal将返回null，必须通过returning = “returnVal”注明参数的名称而且必须与通知函数的参数名称相同。
     * 请注意，在任何通知中这些参数都是可选的，需要使用时直接填写即可，不需要使用时，可以完成不用声明出来。
     *
     * @param joinPoint
     * @param returnVal
     */
    @AfterReturning(value = "execution(* com.iogogogo.aop.service.*.*(..))", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) {
        log.info("后置通知 CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("后置通知 ARGS : {}", Arrays.toString(joinPoint.getArgs()));
        log.info("后置通知 returnVal {} ", returnVal);
    }

    /**
     * 异常通知
     * <p>
     * 该通知只有在异常时才会被触发，并由throwing来声明一个接收异常信息的变量，同样异常通知也用于JoinPoint参数，需要时加上即可。
     *
     * @param e
     */
    @AfterThrowing(value = "execution(* com.iogogogo.aop.service.*.*(..))", throwing = "e")
    public void afterThrowable(Throwable e) {
        log.error("异常: ", e);
    }

    /**
     * 最终通知
     * <p>
     * 该通知有点类似于finally代码块，只要应用了无论什么情况下都会执行。
     *
     * @param joinPoint 可选
     */
    @After("execution(* com.iogogogo.aop.service.*.*(..))")
    public void after(JoinPoint joinPoint) {
        log.info("最终通知 CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("最终通知 ARGS : {}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 环绕通知
     * <p>
     * 第一个参数必须是ProceedingJoinPoint，通过该对象的proceed()方法来执行目标函数，proceed()的返回值就是环绕通知的返回值。
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.iogogogo.aop.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("环绕通知前....");
        // 执行目标函数
        Object obj = joinPoint.proceed();
        log.info("环绕通知后....{}", obj);
        return obj;
    }
}
