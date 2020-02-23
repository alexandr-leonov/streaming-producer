package com.streaming.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class FileController {

    @Autowired
    ResourceLoader resourceLoader;

    /*
     * Get file from resource/static dir
     */
    @MessageMapping("file")
    public Mono<byte[]> getFile(String filename) throws IOException {
        InputStream inputStream = resourceLoader.getClassLoader().getResourceAsStream("static/" + filename);
        return Mono.just(inputStream.readAllBytes());
    }

    /*
     * Play finalized media file(not stream) in browser.
     */
    @MessageMapping("play-file")
    public Mono<byte[]> playMediaFile(String filename) throws IOException {
        InputStream inputStream = resourceLoader.getClassLoader().getResourceAsStream("static/" + filename);
        return Mono.just(inputStream.readAllBytes());
    }

}
