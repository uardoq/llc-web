package com.llc.conscontweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class ConscontwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConscontwebApplication.class, args);
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


}
