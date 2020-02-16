package com.streaming.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;


@Configuration
@EnableReactiveMethodSecurity
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

}


@Data
@AllArgsConstructor
@NoArgsConstructor
class GreetingsRequest {

    private String name;

}

@Data
class GreetingResponse {

    private String greeting;

    GreetingResponse() {
    }

    GreetingResponse(String name) {
        this.withGreeting("Hello " + name + " @ " + Instant.now());
    }


    GreetingResponse withGreeting(String message) {
        this.greeting = message;
        return this;
    }
}

@RestController
class GreetingsRSocketController {

    @MessageMapping("greet")
    GreetingResponse greet ( GreetingsRequest greetingsRequest) {
        return new GreetingResponse(greetingsRequest.getName());
    }
}