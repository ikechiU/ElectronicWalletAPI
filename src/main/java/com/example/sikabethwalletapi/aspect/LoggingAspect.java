package com.example.sikabethwalletapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Ikechi Ucheagwu
 * @created 28/02/2023 - 15:23
 * @project SikabethWalletAPI
 */


@Component
@Slf4j
@Aspect
public class LoggingAspect {

    @Before(value = "serviceMethods()")
    public void loggingAdviceBefore(JoinPoint joinPoint) {
        log.info("Before " + joinPoint.getSignature().getName() +
                " is called. Target object is: {}", joinPoint.getTarget());
    }

    @AfterReturning(pointcut = "paymentMethods() || walletMethods()", returning = "returnedObject")
    public void loggingAdviceAfterReturning(JoinPoint joinPoint, Object returnedObject) {
        log.info("After returning " + joinPoint.getSignature().getName() +
                " is called. Target object is: {}", joinPoint.getTarget());
        log.info("ReturnedObject is {}", returnedObject);
    }

    @AfterThrowing(value = "walletMethods()")
    public void loggingAdviceAfterThrowing(JoinPoint joinPoint) {
        log.info("After throwing " + joinPoint.getSignature().getName() +
                " is called. Target object is: {}", joinPoint.getTarget());
    }

    @Around(value = "@annotation(com.example.sikabethwalletapi.annotation.Loggable)")
    public Object loggingAdviceAround(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;

        try {
            log.info("Before around advice " + proceedingJoinPoint.getSignature().getName() + " is called.");
            returnValue = proceedingJoinPoint.proceed();
            log.info("After returned around advice " + proceedingJoinPoint.getSignature().getName() +
                    " is called. Target object is: {}", proceedingJoinPoint.getTarget());
        } catch (Throwable e) {
            log.error(e.getMessage());
            log.info("After throwing around advice " + proceedingJoinPoint.getSignature().getName() +
                    " is called. Target object is: {}", proceedingJoinPoint.getTarget());
        }

        log.info("After finally around advice " + proceedingJoinPoint.getSignature().getName() +
                " is called. Target object is: {}", proceedingJoinPoint.getTarget());

        return returnValue;
    }

    @Pointcut(value = "execution(* *com.example.sikabethwalletapi.service..*(..))")
    public void serviceMethods() {}

    @Pointcut(value = "within(com.example.sikabethwalletapi.service..*))")
    public void serviceMethods2() {}
    @Pointcut(value = "within(com.example.sikabethwalletapi.service.impl.PaymentServiceImpl))")
    public void paymentMethods() {}
    @Pointcut(value = "within(com.example.sikabethwalletapi.service.impl.WalletServiceImpl))")
    public void walletMethods() {}

}
