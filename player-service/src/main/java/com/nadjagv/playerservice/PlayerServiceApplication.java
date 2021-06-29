
package com.nadjagv.playerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories("com.nadjagv.*")
//@ComponentScan(basePackages = { "com.nadjagv.*" })
@EntityScan("com.nadjagv.*")
public class PlayerServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(PlayerServiceApplication.class, args);
    }

}

