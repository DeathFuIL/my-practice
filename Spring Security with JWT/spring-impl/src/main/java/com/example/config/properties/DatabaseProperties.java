package com.example.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class DatabaseProperties {

    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;
    private final Integer poolSize;

    public DatabaseProperties(@Value("${spring.datasource.url}") String url,
                              @Value("${spring.datasource.hikari.max-pool-size}") Integer poolSize,
                              @Value("${spring.datasource.username}") String username,
                              @Value("${spring.datasource.password}") String password,
                              @Value("${spring.datasource.driver.classname}") String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.poolSize = poolSize;
    }
}
