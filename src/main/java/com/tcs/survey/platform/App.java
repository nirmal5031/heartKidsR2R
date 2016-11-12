package com.tcs.survey.platform;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan("com.tcs.survey.platform")
@Configuration
public class App extends SpringBootServletInitializer {
    public static void main(final String[] args) {
        SpringApplication.run(new Object[]{App.class}, args);
    }

    @Override
    protected SpringApplicationBuilder configure(
            final SpringApplicationBuilder application) {
        return application.sources(new Object[]{App.class});
    }

    @Bean
    @Profile({"dev"})
    ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
    
    
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/cms_content.json").addResourceLocations("classpath:");
        }
    
    
    
}
