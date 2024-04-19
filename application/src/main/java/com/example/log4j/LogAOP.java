package com.example.log4j;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class LogAOP {
    @Around("@annotation(LogRunningTime)")
    public Object logRunningTime(ProceedingJoinPoint joinPoint) throws Throwable { // 메서드 러닝타임 재는 AOP
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint()); // Spring 5.2부터는 단위가 ns
        return proceed;
    }
}
