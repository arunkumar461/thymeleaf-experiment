package com.moj.thymeleafexperiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@SpringBootApplication
@Controller
public class ThymeleafExperimentApplication {

    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new GovUKDialect());
        return templateEngine;
    }

    @GetMapping("/")
    public String test() {
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(ThymeleafExperimentApplication.class, args);
    }

}
