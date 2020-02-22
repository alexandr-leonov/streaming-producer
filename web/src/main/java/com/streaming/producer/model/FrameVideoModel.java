package com.streaming.producer.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FrameVideoModel {

    private final byte[] content;

}
