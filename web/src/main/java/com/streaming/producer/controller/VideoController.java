package com.streaming.producer.controller;

import com.streaming.producer.ProducerApplication;
import com.streaming.producer.adapter.VideoAdapter;
import com.streaming.producer.model.FrameVideoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class VideoController {

    @Autowired
    VideoAdapter videoAdapter;

    /*
     * Get screenshot from cam
     */
    @MessageMapping("cam-capture")
    public Mono<byte[]> getCapture(String cameraName) {
        return Mono.just(videoAdapter.getCapture(cameraName));
    }


    //TODO add context
    //TODO add custom serializers/deserializers
    @MessageMapping("cam-stream")
    Flux<FrameVideoModel> getVideo(String cameraName) {
        return Flux.fromStream(Stream.iterate(0L, n -> ++n)
                .map(iterator -> new FrameVideoModel(iterator, videoAdapter.getCapture(cameraName))))
                .log()
                .delayElements(Duration.ofSeconds(1));
    }

    //TODO add unsubscribing by context at all infinite streams
    @MessageMapping("cam-exit")
    public Mono<Boolean> disableCamera() {
        return Mono.defer(() -> {
                    ProducerApplication.restart();
                    return Mono.empty();
                })
                .then(Mono.just(videoAdapter.disableCamera()));
    }

}
