package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.Application;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

//To do autowired. u must run these two lines
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StorageServiceImplTest {
    @Autowired
    private StorageServiceImpl storageService;

    @Autowired
    private RecipeService recipeService;




}