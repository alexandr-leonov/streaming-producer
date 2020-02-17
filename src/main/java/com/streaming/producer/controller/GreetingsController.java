package com.streaming.producer.controller;

import com.streaming.producer.model.GreetingsResponse;
import com.streaming.producer.model.GreetingsRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;

/*
 * Unfortunately functional endpoints not supported Rsocket yet :(
 *
 * Issue: https://github.com/spring-projects/spring-framework/issues/23135
  */
@RestController
public class GreetingsController {

    @MessageMapping("greet")
    Mono<GreetingsResponse> greet(GreetingsRequest request) {
        return Mono.just(new GreetingsResponse("Hello " + request.getName() + " @ " + Instant.now()));
    }
}
