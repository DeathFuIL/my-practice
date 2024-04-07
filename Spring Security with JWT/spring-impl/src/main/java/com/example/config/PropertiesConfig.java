package com.example.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertiesConfig {


    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(
            YamlPropertiesFactoryBean yamlPropertiesFactoryBean) {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
                new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setProperties(yamlPropertiesFactoryBean.getObject());
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public YamlPropertiesFactoryBean yamlPropertiesFactoryBean() {
        YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
        propertiesFactoryBean.setResources(new ClassPathResource("application.yaml"));
        return propertiesFactoryBean;
    }
}
