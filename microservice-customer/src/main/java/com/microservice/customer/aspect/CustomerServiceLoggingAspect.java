package com.microservice.customer.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomerServiceLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceLoggingAspect.class);

    @Before("execution(public * com.microservice.customer.service.impl.CustomerService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info("Starting execution: {} with arguments: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(public * com.microservice.customer.service.impl.CustomerService.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        LOGGER.info("Method {} completed successfully returning: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(public * com.microservice.customer.service.impl.CustomerService.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        LOGGER.error("Method {} threw exception: {}", joinPoint.getSignature().getName(), error.getMessage());
    }
}
