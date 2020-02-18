package com.streaming.producer.controller;

import com.streaming.producer.model.GreetingsRequest;
import com.streaming.producer.model.GreetingsResponse;
import io.rsocket.RSocketFactory;
import org.springframework.boot.rsocket.server.ServerRSocketFactoryProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

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

    @MessageMapping("greet-stream")
    Flux<GreetingsResponse> infiniteGreet(GreetingsRequest request) {
        return Flux.fromStream(Stream.generate(() ->
                new GreetingsResponse("Hello " + request.getName() + " @ " + Instant.now())))
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    //Support resuming producer after non available period.
    //Consumer not need restart his application on error cause.
    @Bean
    ServerRSocketFactoryProcessor serverRSocketFactoryProcessor() {
        return RSocketFactory.ServerRSocketFactory::resume;
    }
}
