package com.streaming.producer.controller;

import com.streaming.producer.adapter.VideoAdapter;
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

    @Autowired
    VideoAdapter videoAdapter;

    /*
     * Get file from resource/static dir
     */
    @MessageMapping("file")
    public Mono<byte[]> getFile(String filename) throws IOException {
        InputStream inputStream = resourceLoader.getClassLoader().getResourceAsStream("static/" + filename);
        return Mono.just(inputStream.readAllBytes());
    }

    /*
    * Get screenshot from cam
    */
    @MessageMapping("cam-capture")
    public Mono<byte[]> getCapture(String cameraName) {
        return Mono.just(videoAdapter.getCapture(cameraName));
    }

    /*
     * Play finalized media file(not stream) in browser.
     */
    @MessageMapping("play-file")
    Mono<byte[]> playMediaFile(String filename) throws IOException {
        InputStream inputStream = resourceLoader.getClassLoader().getResourceAsStream("static/" + filename);
        return Mono.just(inputStream.readAllBytes());
    }

}
