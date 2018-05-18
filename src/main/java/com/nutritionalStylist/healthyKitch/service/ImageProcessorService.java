package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public interface ImageProcessorService {

    BufferedImage createResizedImage(ImageQualityType imageQualityType, BufferedImage imageFile);

    HashMap<ImageQualityType, BufferedImage> processImageFile(BufferedImage imageFile);

}
