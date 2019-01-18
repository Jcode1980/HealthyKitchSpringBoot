package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.MealTypeFile;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.stream.Stream;

public interface StorageService {

    //void init();

    //void store(MultipartFile file);

    //Returns processed images
    HashMap<ImageQualityType, BufferedImage> processImage(MultipartFile file) throws Exception;

    //Stream<Path> loadAll();

    //Path load(String filename);

    Resource loadAsResource(String filePath);

    Resource resourceForFileID(Integer id);

    Resource recipeImageAsResource(int recipeImageID, int quality);

    MealTypeFile mealTypeFileForMealType(MealType mealType, MultipartFile file) throws Exception;
    //void deleteAll();

}
