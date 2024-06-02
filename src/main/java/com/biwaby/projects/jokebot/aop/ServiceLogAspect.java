package com.biwaby.projects.jokebot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
public class ServiceLogAspect {

    @Pointcut("execution(public * com.biwaby.projects.jokebot.service.implementations.*.*(..))")
    public void servicePointcut() {}

    @Before("servicePointcut()")
    public void beforeCallService(JoinPoint joinPoint) {
        List<String> args = Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .toList();
        log.info("\u001B[32m[CALL SERVICE]: called method {} with args {}\u001B[0m", joinPoint.getSignature().getName(), args);
    }

    @AfterReturning(value = "servicePointcut()", returning = "object")
    public void afterCallService(JoinPoint joinPoint, Object object) {
        log.info("\u001B[32m[SERVICE]: method {} returned {}\u001B[0m", joinPoint.getSignature().getName(), object);
    }
}
