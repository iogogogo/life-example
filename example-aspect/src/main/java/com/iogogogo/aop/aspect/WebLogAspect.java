package com.iogogogo.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by tao.zeng on 2020/2/16.
 * <p>
 * http://blog.didispace.com/springbootaoplog/
 */
@Slf4j
@Aspect
@Configuration
public class WebLogAspect {

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    @Pointcut("execution(public * com.iogogogo.aop.api.*Api*.*(..))")
    public void webLog() {
    }

    /**
     * 前置通知@Before
     *
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(final JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        Optional.ofNullable(attributes).ifPresent(x -> {
            HttpServletRequest request = x.getRequest();

            // 记录下请求内容
            log.info("URL : " + request.getRequestURL().toString());
            log.info("HTTP_METHOD : " + request.getMethod());
            log.info("IP : " + getIp(request));
            log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

            Map<String, Object> map = new HashMap<>();
            map.put("ARGS", joinPoint.getArgs());
            map.put("METHOD", joinPoint.getSignature().getName());
            threadLocal.set(map);
        });
    }

    /**
     * 后置通知@AfterReturning
     *
     * @param joinPoint
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        // 处理完请求，返回内容
        // log.debug("RESPONSE : " + ret);
        Map<String, Object> map = threadLocal.get();
        if (MapUtils.isNotEmpty(map)) {
            Object[] args = (Object[]) map.get("ARGS");
            Object method = map.get("METHOD");
        }
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
