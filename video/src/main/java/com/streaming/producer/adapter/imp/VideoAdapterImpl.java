package com.streaming.producer.adapter.imp;

import com.streaming.producer.adapter.VideoAdapter;
import com.streaming.producer.model.CamType;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class VideoAdapterImpl implements VideoAdapter {

    @Override
    public byte[] getCapture() {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(CamType.WEB_CAM.getCode());
        try{
            grabber.start();
            Frame frame = grabber.grab();
            BufferedImage buffer = new Java2DFrameConverter().convert(frame);
            grabber.stop();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffer, "jpg", baos );
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();
            grabber.release();
            grabber.close();
            return bytes;
        } catch (IOException e) {
            log.error("Error in VideoApapter.getImage = {}", e.getMessage());
            throw new IllegalArgumentException("Sorry video cam is not available :(");
        }
    }
}
