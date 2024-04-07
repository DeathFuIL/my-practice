package ru.kpfu.itis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@PropertySource("classpath:application.yaml")
@EnableWebMvc
@EntityScan(basePackages = "ru.kpfu.itis.models")
public class WorkTracerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkTracerApplication.class, args);
    }

}
