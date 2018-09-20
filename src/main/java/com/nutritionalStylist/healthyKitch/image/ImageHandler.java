package com.nutritionalStylist.healthyKitch.image;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import com.nutritionalStylist.healthyKitch.model.ImageHolder;
import com.nutritionalStylist.healthyKitch.service.RecipeServiceImpl;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ImageHandler {
    private Logger log = Logger.getLogger(ImageHandler.class);
    @Autowired
    private StorageService storageService;


    public ImageHandler(){ }

    public void processAndSaveFile(ImageHolder imageHolder, MultipartFile file) throws Exception{
        //TODO: tmp file should be removed.
        String fileExtension =  imageHolder.getOrginalImage().get().fileExtension();
        HashMap<ImageQualityType, BufferedImage> imagesMap =  storageService.processAndStoreImage(file);


//        ImageIO.write(imagesMap.get(ImageQualityType.PREVIEW), fileExtension, new java.io.File(recipeImage.displayPreviewImagePath()));
//        ImageIO.write(imagesMap.get(ImageQualityType.THUMBNAIL), fileExtension, new java.io.File(recipeImage.displayThumbnailImagePath()));
//        ImageIO.write(imagesMap.get(ImageQualityType.ORIGINAL), fileExtension, new java.io.File(recipeImage.displayOriginalImagePath()));
        imageHolder.getPreviewImage().get().processBufferedImage(imagesMap.get(ImageQualityType.PREVIEW));
        imageHolder.getThumbnailImage().get().processBufferedImage(imagesMap.get(ImageQualityType.THUMBNAIL));
        imageHolder.getOrginalImage().get().processBufferedImage(imagesMap.get(ImageQualityType.ORIGINAL));

        log.info("this is the previewPath : " + imageHolder.displayPreviewImagePath());
        log.info("this is the originalPath : " + imageHolder.displayOriginalImagePath());
        log.info("this is the thumbnailPath : " + imageHolder.displayThumbnailImagePath());

    }


    public StorageService getStorageService() {
        return storageService;
    }

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }
}
