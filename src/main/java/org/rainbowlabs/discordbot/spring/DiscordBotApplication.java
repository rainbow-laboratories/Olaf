package org.rainbowlabs.discordbot.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("org.rainbowlabs")
@EnableJpaRepositories("org.rainbowlabs.discordbot.spring.persistence")
@EntityScan("org.rainbowlabs.discordbot.spring.persistence")
public class DiscordBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscordBotApplication.class, args);
    }
}
