package ru.kpfu.itis.starter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExecutionTime {

    //The upper limit of the time (in millis) execution after which the warning is thrown
    float validTime();

}
