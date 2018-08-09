package com.llc.conscontweb;

import com.llc.conscontweb.helpers.CCWHelpers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class ConscontwebApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ConscontwebApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
    }

    @Bean
    public MessageSource messageSource() {
        // make messages.properties values available to spring
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // messages.properties should be located at the root of resources/
        messageSource.setBasenames("classpath:/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean
    public CCWHelpers ccwHelpers() {
        return CCWHelpers.getInstance();
    }

}
