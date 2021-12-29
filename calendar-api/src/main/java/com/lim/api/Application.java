package com.lim.api;

import com.lim.core.SimpleEntity;
import com.lim.core.SimpleEntityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableJpaAuditing
@EntityScan("com.lim.core")
@EnableJpaRepositories("com.lim.core")
@RestController
@SpringBootApplication
public class Application {

    private final SimpleEntityRepository simpleEntityRepository;

    public Application(SimpleEntityRepository simpleEntityRepository) {
        this.simpleEntityRepository = simpleEntityRepository;
    }

    @GetMapping("/")
    public List<SimpleEntity> findALl() {
        return simpleEntityRepository.findAll();
    }

    @PostMapping("/save")
    public SimpleEntity save() {
        final SimpleEntity s = new SimpleEntity();
        s.setName("hello world");
        return simpleEntityRepository.save(s);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
