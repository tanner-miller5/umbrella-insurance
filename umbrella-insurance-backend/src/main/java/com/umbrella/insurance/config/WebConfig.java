/*
 * Copyright (c) 2022  Umbrella Insurance Inc.
 */

package com.umbrella.insurance.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * WebConfig class.
 * </p>
 *
 * @author tanner
 * @version $Id: $Id
 * @since 0.0.19
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig
    implements ApplicationContextAware, WebMvcConfigurer {

    private ApplicationContext applicationContext;

    /**
     * <p>
     * Constructor for WebConfig.
     * </p>
     */
    public WebConfig() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * <p>
     * messageSource.
     * </p>
     *
     * @return a {@link org.springframework.context.support.ResourceBundleMessageSource} object
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("Messages");
        return messageSource;
    }
}
