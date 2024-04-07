package ru.kpfu.itis.starter.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AnnotationAspect {

    @Pointcut("@annotation(ru.kpfu.itis.starter.annotation.ExecutionTime)")
    public void allMethodsWithExecutionTimeAnnotation() { }



}
