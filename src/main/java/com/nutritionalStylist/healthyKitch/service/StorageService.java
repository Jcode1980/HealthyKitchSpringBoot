package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.Recipe;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    //void init();

    //void store(MultipartFile file);

    void processAndStoreImage(Recipe recipe, MultipartFile file) throws Exception;

    //Stream<Path> loadAll();

    //Path load(String filename);

    Resource loadAsResource(String filePath);

    //void deleteAll();

}
