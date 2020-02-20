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

    private static OpenCVFrameGrabber grabber;

    @Override
    public byte[] getCapture(String cameraName) {
        grabber = new OpenCVFrameGrabber(CamType.getByName(cameraName).getCode());
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
            return bytes;
        } catch (IOException e) {
            log.error("Error in VideoApapter.getImage = {}", e.getMessage());
            throw new IllegalArgumentException("Sorry video cam is not available :(");
        }
    }

    // TODO think about infinite frame streaming
    @Override
    public byte[] getVideo(String cameraName) {
        throw new IllegalStateException("Not implemented yet!");
    }

    // TODO add "disable camera" method after closing rsocket
}
