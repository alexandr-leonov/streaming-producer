package com.streaming.producer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  CamType {
    WEB_CAM(0),
    USB_CAM(1);

    private int code;
}
