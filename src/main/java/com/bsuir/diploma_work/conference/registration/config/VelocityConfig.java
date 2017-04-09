package com.bsuir.diploma_work.conference.registration.config;


import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class VelocityConfig {


    private static final String RESOURCE_LOADER = "resource.loader";
    private static final String FILE_RESOURCE_LOADER_CLASS = "file.resource.loader.class";

    @Value("${resource.loader}")
    private String resourceLoader;

    @Value("${file.resource.loader.class}")
    private String resourceLoaderClass;

    @Bean
    public VelocityEngine velocityEngine() throws VelocityException, IOException {
        VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
        Properties props = new Properties();
        props.put(RESOURCE_LOADER, resourceLoader);
        props.put(FILE_RESOURCE_LOADER_CLASS, resourceLoaderClass);
        factory.setVelocityProperties(props);

        return factory.createVelocityEngine();
    }
}
