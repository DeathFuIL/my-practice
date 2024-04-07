package ru.kpfu.itis.starter.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ControllerAspect {

    @Pointcut("execution(* ru.kpfu.itis.controllers.*.*(..))")
    public void allControllersMethods() { }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void allControllersPostMethods() { }


}
