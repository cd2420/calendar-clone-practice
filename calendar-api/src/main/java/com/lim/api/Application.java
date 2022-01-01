package com.lim.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EntityScan("com.lim.core")
@EnableJpaRepositories("com.lim.core")
@SpringBootApplication(scanBasePackages = "com.lim")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
