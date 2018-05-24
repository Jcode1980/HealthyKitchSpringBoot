package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessorServiceImplTest {

    ImageProcessorServiceImpl imageProcessorService;

    @Before
    public void setUp() {
        imageProcessorService = new ImageProcessorServiceImpl();
    }


    @Test
    public void createResizedImage_shoulCreateThumbnail(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("/Users/johnadolfo/Desktop/test.png"));
            BufferedImage newImage = imageProcessorService.createResizedImage(ImageQualityType.PREVIEW, img);
            ImageIO.write(newImage, "png", new File("/Users/johnadolfo/Desktop/transferred.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}