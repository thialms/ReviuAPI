package br.edu.fatecpg.reviu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReviuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviuApplication.class, args);
    }

}
