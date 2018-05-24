package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;

@Service
public class ImageProcessorServiceImpl implements ImageProcessorService {

    @Override
    public BufferedImage createResizedImage(ImageQualityType imageQualityType, BufferedImage imageFile) {
        return Scalr.resize(imageFile, Scalr.Method.ULTRA_QUALITY, imageQualityType.getWidth(), imageQualityType.getHeight());
    }

    @Override
    public HashMap<ImageQualityType, BufferedImage> processImageFile(BufferedImage imageFile) {
        HashMap<ImageQualityType, BufferedImage> hashMap = new HashMap<>();
        BufferedImage previewImage =  Scalr.resize(imageFile, Scalr.Method.ULTRA_QUALITY, ImageQualityType.PREVIEW.getWidth(), ImageQualityType.PREVIEW.getHeight());
        BufferedImage thumbnailImage =  Scalr.resize(imageFile, Scalr.Method.ULTRA_QUALITY, ImageQualityType.THUMBNAIL.getWidth(), ImageQualityType.THUMBNAIL.getHeight());

        hashMap.put(ImageQualityType.PREVIEW, previewImage);
        hashMap.put(ImageQualityType.THUMBNAIL, thumbnailImage);
        hashMap.put(ImageQualityType.ORIGINAL, imageFile);

        return hashMap;
    }
}
