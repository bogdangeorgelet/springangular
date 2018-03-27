package com.example.springangularapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan("com.example.springangularapp")
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/static/app/scripts/**")
                .addResourceLocations("/resources/static/app/scripts/");
        registry.addResourceHandler("/resources/static/app/styles/**")
                .addResourceLocations("/resources/static/app/styles");
        registry.addResourceHandler("/resources/static/app/views/**")
                .addResourceLocations("/resources/static/app/views/");
        registry.addResourceHandler("/resources/static/app/**")
                .addResourceLocations("/resources/static/app");
        registry.addResourceHandler("/resources/static/**")
                .addResourceLocations("/resources/static/");
    }

    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
