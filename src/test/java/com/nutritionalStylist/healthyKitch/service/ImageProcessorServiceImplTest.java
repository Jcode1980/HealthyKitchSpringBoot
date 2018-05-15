package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.enums.ImageType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

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
            img = ImageIO.read(new File("/Users/johnadolfo/Desktop/WorkRelated/HK/File/036892.jpg"));
            BufferedImage newImage = imageProcessorService.createResizedImage(ImageType.PREVIEW, img);
            ImageIO.write(newImage, "jpg", new File("/Users/johnadolfo/Desktop/WorkRelated/HK/PreviewFIle/TestThumbnail.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}