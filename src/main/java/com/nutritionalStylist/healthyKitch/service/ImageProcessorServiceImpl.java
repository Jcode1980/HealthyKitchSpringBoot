package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.enums.ImageType;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessorServiceImpl implements ImageProcessorService {

    @Override
    public BufferedImage createResizedImage(ImageType imageType, BufferedImage imageFile) {
        return Scalr.resize(imageFile, Scalr.Method.ULTRA_QUALITY, imageType.getWidth(), imageType.getHeight());
    }
}
