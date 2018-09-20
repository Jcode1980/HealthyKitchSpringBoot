package com.nutritionalStylist.healthyKitch.controller;

import com.nutritionalStylist.healthyKitch.model.File;
import com.nutritionalStylist.healthyKitch.repository.RecipeImageRepository;
import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageFileNotFoundException;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/files")
public class FileController {
    private Logger log = Logger.getLogger(FileController.class);
    private final StorageService storageService;
    private final RecipeService recipeService;

    @Autowired
    public FileController(RecipeService recipeService, StorageService storageService) {
        this.storageService = storageService;
        this.recipeService = recipeService;

    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<?> handleStorageFileNotFound(Exception exc) {
        return ResponseEntity.notFound().build();
    }


//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(RecipeController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }
//
//    @GetMapping(value ="/files/{filename:.+}",
//        produces=MediaType.IMAGE_JPEG_VALUE
//    )
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }


    @GetMapping(value ="/Images/{fileID}",
            produces=MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<Resource> serveImage(@PathVariable Integer fileID) {
        log.info("got ehre serveImage");
        Resource file = null;
        try{
            file = storageService.resourceForFileID(fileID);
        }catch(Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping(value = "/RecipeImage/{recipeImageID}",
            produces=MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> serveRecipeImage(@PathVariable int recipeImageID,
                                                     @RequestParam(value = "quality", required = false) Integer quality) {
        log.info("got here");
        //Resource file = storageService.loadAsResource(filename);
        Resource file = storageService.recipeImageAsResource(recipeImageID, quality != null ? quality : 1);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }



    @ExceptionHandler(StorageFileNotFoundException.class)
    private ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}
