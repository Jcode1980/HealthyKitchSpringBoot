package com.nutritionalStylist.healthyKitch.service;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import com.nutritionalStylist.healthyKitch.model.File;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.RecipeFile;
import com.nutritionalStylist.healthyKitch.model.RecipeImage;
import com.nutritionalStylist.healthyKitch.repository.FileRepository;
import com.nutritionalStylist.healthyKitch.repository.RecipeImageRepository;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;

@Service
public class StorageServiceImpl implements StorageService {
    public static Logger log = Logger.getLogger(StorageServiceImpl.class);
    private RecipeRepository recipeRepository;
    private RecipeImageRepository recipeImageRepository;
    private FileRepository fileRepository;
    private ImageProcessorService imageProcessorService;

    @Value("${com.nutritionalStylist.ROOT_FOLDER}")
    protected String rootFolder;

    @Value("${com.nutritionalStylist.TMP_FOLDER}")
    protected String tmpFolder;

    @Autowired
    public StorageServiceImpl(FileRepository fileRepository, RecipeRepository recipeRepository, ImageProcessorService imageProcessorService,
                              RecipeImageRepository recipeImageRepository) {
        this.recipeRepository = recipeRepository;
        this.fileRepository = fileRepository;
        this.imageProcessorService = imageProcessorService;
        this.recipeImageRepository = recipeImageRepository;

        //this.tempFolderPath = Paths.get(tmpFolder);
    }


//    public void store(MultipartFile file) {
//        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//        try {
//            if (file.isEmpty()) {
//                throw new StorageException("Failed to store empty file " + filename);
//            }
//            if (filename.contains("..")) {
//                // This is a security check
//                throw new StorageException(
//                        "Cannot store file with relative path outside current directory "
//                                + filename);
//            }
//            try (InputStream inputStream = file.getInputStream()) {
//                Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
//            }
//        }
//        catch (IOException e) {
//            throw new StorageException("Failed to store file " + filename, e);
//        }
//    }

    private String storeToTempFile(MultipartFile file) throws Exception{
        System.out.println("what is the tmp folder? " + tmpFolder);
        System.out.println("what is the root folder? " + rootFolder);
        String fullTmpPath = rootFolder + tmpFolder;
        Path tempPath = Paths.get(fullTmpPath);

        String filename = StringUtils.cleanPath(file.getOriginalFilename());


        //String tmpFolder = System.getProperty("com.nutritionalStylist.TMP_FOLDER");
        log.info("temp folder is: " + fullTmpPath + " original file name: " + file.getOriginalFilename());
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().
                lastIndexOf('.') +1, file.getOriginalFilename().length());

        String tmpFileName  = File.getUniqueFileName(fullTmpPath, fileExtension);


        log.info("Unique file name is : " + tmpFileName);


        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + file);
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
        InputStream inputStream = file.getInputStream();
        Files.copy(inputStream, tempPath.resolve(tmpFileName), StandardCopyOption.REPLACE_EXISTING);

        return tmpFileName;
    }

    @Override
    public HashMap<ImageQualityType, BufferedImage> processAndStoreImage(Recipe recipe, MultipartFile file) throws Exception {
        //Check if file is image

        //store file in tmp folder
        String tmpFile = storeToTempFile(file);

        //TODO: check if file is image.
        //Process the file.
        BufferedImage img = ImageIO.read(new java.io.File(tmpFile));
        HashMap<ImageQualityType, BufferedImage> allRecipeFilesForImage = imageProcessorService.processImageFile(img);
        return allRecipeFilesForImage;

        //create recipeImage for file for the Recipe
        //Optional<Recipe> recipeOptional = recipeRepository.findById(recipeID);

        //do throw here if not found
        //Recipe recipe = recipeOptional.get(recipeID);


        //store the file in original path for recipeImage

        //do a save.
    }

//    @Override
//    public Stream<Path> loadAll() {
//        try {
//            return Files.walk(this.tempFolderPath, 1)
//                    .filter(path -> !path.equals(this.tempFolderPath))
//                    .map(this.tempFolderPath::relativize);
//        }
//        catch (IOException e) {
//            throw new StorageException("Failed to read stored files", e);
//        }
//
//    }

//    @Override
//    public Path load(String filename) {
//        return tempFolderPath.resolve(filename);
//    }


    //fix me
    @Override
    public Resource recipeImageAsResource(int recipeImageID, int quality){
        Optional<RecipeImage> recipeImage = recipeImageRepository.findById(recipeImageID);
        System.out.println("found recipe image?? " + recipeImage.get());
        ImageQualityType imageQualityType = ImageQualityType.imageTypeForID(quality);

        Optional<RecipeFile> recipeFile = recipeImage.get().recipeFileForImageType(imageQualityType);
        String filePath = recipeFile.get().filePath();
        log.info("looking for recipeimage with filePath: " + filePath);

        return loadAsResource(filePath);

    }



    public Resource loadAsResource(String filePath) {
        try {
            Path file = Paths.get(filePath);
            //Path file = Paths.get("/Users/johnadolfo/Desktop/31");
            //Path file = Paths.get("/Users/johnadolfo/Desktop/test.png");
            System.out.println("fund file path : "  + file);

            Resource resource = new UrlResource(file.toUri());


            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                System.out.println("Could not read file");
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filePath);

            }
        }
        catch (MalformedURLException e) {
            System.out.println("Could not read file catch");
            throw new StorageFileNotFoundException("Could not read file: " + filePath, e);
        }
    }

//    @Override
//    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(tempFolderPath.toFile());
//    }

//    @Override
//    public void init() {
//        try {
//            Files.createDirectories(tempFolderPath);
//        }
//        catch (IOException e) {
//            throw new StorageException("Could not initialize storage", e);
//        }
//    }
}
