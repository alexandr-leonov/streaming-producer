package com.streaming.producer.adapter;

public interface VideoAdapter {

    byte[] getCapture(String cameraName);

    byte[] getVideo(String cameraName);

}
