package ru.kpfu.itis.starter.config;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StopWatch;
import ru.kpfu.itis.starter.annotation.ExecutionTime;

import java.lang.reflect.Method;
import java.util.UUID;

@Log4j2
@Configuration
@Aspect
@ConditionalOnProperty(prefix = "ru.kpfu.itis.logger", value = "enabled", havingValue = "true", matchIfMissing = true)
@PropertySource("classpath:application.properties")
public class LoggerConfig {

    public LoggerConfig() {
        System.out.println("Logger on...");
    }

    private static final String TIME_WARNING_PATTERN = "time execution of method {} with args [{}] is out of {} ({})";

    private static final String BEFORE_METHOD_MESSAGE_PATTERN = "method {} call args: [{}]";

    private static final String AFTER_METHOD_MESSAGE_PATTERN = "method {} result: [{}]";

    private static final String AFTER_POST_METHOD_MESSAGE_PATTERN = "new account with uuid = {}";

    @Around("ru.kpfu.itis.starter.aspect.AnnotationAspect.allMethodsWithExecutionTimeAnnotation()")
    public Object aroundMethod(ProceedingJoinPoint jp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        float validTime = method.getAnnotation(ExecutionTime.class).validTime();

        try {
            stopWatch.start(jp.toShortString());
            return jp.proceed();
        } finally {
            stopWatch.stop();
            if (stopWatch.getTotalTimeMillis() > validTime) {
                log.warn(TIME_WARNING_PATTERN, jp.getSignature(), jp.getArgs(), validTime, stopWatch.getTotalTimeMillis());
            }
        }
    }

    @Before("ru.kpfu.itis.starter.aspect.ControllerAspect.allControllersMethods()")
    public void beforeControllerMethod(JoinPoint jp) {
        log.info(BEFORE_METHOD_MESSAGE_PATTERN, jp.getSignature(), jp.getArgs());
    }

    @AfterReturning(value = "ru.kpfu.itis.starter.aspect.ControllerAspect.allControllersMethods()", returning = "result")
    public void afterControllerMethod(JoinPoint jp, Object result) {
        log.info(AFTER_METHOD_MESSAGE_PATTERN, jp.getSignature(), result);
    }

    @AfterReturning(value = "ru.kpfu.itis.starter.aspect.ControllerAspect.allControllersPostMethods()", returning = "uuid")
    public void afterControllerPostMethod(JoinPoint jp, UUID uuid) {
        log.info(AFTER_POST_METHOD_MESSAGE_PATTERN, uuid);
    }

}
