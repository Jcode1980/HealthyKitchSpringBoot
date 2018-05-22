package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.Application;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

//To do autowired. u must run these two lines
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StorageServiceImplTest {
    @Autowired
    private StorageServiceImpl storageService;

    @Autowired
    private RecipeService recipeService;


    @Test
    public void testForFileReadiness() {

        String filePath = "/Users/johnadolfo/Desktop/WorkRelated/HK/Production/RecipeImage/original/43.png";
        try{

        Path file = Paths.get(filePath);
        Resource resource = new UrlResource(file.toUri());


        if (resource.exists() || resource.isReadable()) {
            System.out.println("i can read");
        } else {
            System.out.println("Could not read file");
            throw new StorageFileNotFoundException(
                    "Could not read file: " + filePath);

        }
    } catch (MalformedURLException e) {
            System.out.println("Could not read file catch");
            throw new StorageFileNotFoundException("Could not read file: " + filePath, e);
        }
    }


}