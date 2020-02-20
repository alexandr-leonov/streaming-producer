package com.streaming.producer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CamType {
    WEB_CAM(0, "web"),
    USB_CAM(1, "usb");

    private int code;
    private String cameraName;

    public static CamType getByName(String name) {
        if (WEB_CAM.cameraName.equals(name)) {
            return WEB_CAM;
        } else if (USB_CAM.cameraName.equals(name)) {
            return USB_CAM;
        } else throw new IllegalArgumentException("Camera with \"" + name + "\" name not found!");
    }

}
