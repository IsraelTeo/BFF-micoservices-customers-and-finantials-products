package com.microservice.product.aspect;

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
public class ProductServiceLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceLoggingAspect.class);

    @Before("execution(public * com.microservice.product.service.impl.ProductService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info("Starting execution: {} with arguments: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(public * com.microservice.product.service.impl.ProductService.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        LOGGER.info("Method {} completed successfully returning: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(public * com.microservice.product.service.impl.ProductService.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        LOGGER.error("Method {} threw exception: {}", joinPoint.getSignature().getName(), error.getMessage());
    }
}
