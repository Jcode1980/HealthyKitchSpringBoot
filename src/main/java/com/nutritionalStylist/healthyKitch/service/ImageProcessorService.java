package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.enums.ImageType;

import java.awt.image.BufferedImage;
import java.io.File;

public interface ImageProcessorService {
    public static final int THUMBNAIL = 1;
    public static final int PREVIEW = 2;

    public abstract BufferedImage createResizedImage(ImageType imageType, BufferedImage imageFile);

}
