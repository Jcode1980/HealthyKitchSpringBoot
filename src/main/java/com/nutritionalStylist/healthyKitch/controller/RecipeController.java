package com.nutritionalStylist.healthyKitch.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.model.dto.Views;
import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private Logger log = Logger.getLogger(RecipeController.class);
    private final RecipeService recipeService;
    private final StorageService storageService;
    //private ModelMapper modelMapper;

    @Autowired
    public RecipeController(RecipeService recipeService, StorageService storageService) {
        this.storageService = storageService;
        this.recipeService = recipeService;

//        modelMapper = new ModelMapper();
//        PropertyMap<Recipe, RecipeDto> recipeMap = new PropertyMap<Recipe, RecipeDto>() {
//            protected void configure() {
//                //map(source.getDefaultImage(, destination.getDefaultImageID());
//                map(source.sortedMeasuredIngredients(), destination.getMeasuredIngredients());
//                map(source.getInstructions(), destination.getInstructions());
//                map(source.sortedDietaryCategories(), destination.getDietaryCategories());
//            }
//        };
//
//
//        modelMapper.addMappings(recipeMap);
    }


//    @GetMapping("/allRecipes")
//    public Collection<RecipeDto> getAllRecipes(){
//        log.info("got to all recipes");
//        return recipeService.findAllRecipes().stream().map(recipe -> RecipeDto.convertToDto(recipe)).
//                collect(Collectors.toList());
//    }

    @JsonView(Views.ListView.class)
    @GetMapping("/recipes")
    public Collection<RecipeDto> searchRecipesByDTO(RecipeSearchDto searchDto) {
        Collection<Recipe> recipes = recipeService.findRecipesUsingRecipeDTO(searchDto);
        log.info("found recipes : " + recipes);
        return recipes.stream().map(recipe -> RecipeDto.convertToDto(recipe)).collect(Collectors.toList());
    }

    @JsonView(Views.DetailedView.class)
    @GetMapping(value = "/{recipeID}")
    public RecipeDto getRecipeById(@PathVariable("recipeID") int recipeID) {
        Recipe recipe = recipeService.findRecipeByID(recipeID).orElseThrow(IllegalArgumentException::new);
        log.info("how many ingredients??" + recipe.getMeasuredIngredients().size());
        return RecipeDto.convertToDto(recipe);
    }

    @GetMapping(value = "/reviews/{recipeID}")
    public Collection<RecipeReview> recipeReviewsForRecipe(@PathVariable("recipeID") int recipeID) throws Exception{
        Collection<RecipeReview> recipeReviews = recipeService.reviewsForRecipe(recipeID);
        return recipeReviews;
    }

    @GetMapping("/allMealTypes")
    public Collection<MealType> getAllMealtypes(Authentication authentication){
        //Object user = authentication.getPrincipal();
        //log.info("user is : " + user);
        return recipeService.findAllMealTypes(); }


    @GetMapping("/allMetrics")
    public Collection<Metric> getAllMetrics(){ return recipeService.findAllMetrics(); }


    @GetMapping("/allNutritionalBenefits")
    public Collection<NutritionalBenefit> getAllNutritionalBenefit(){ return recipeService.findAllNutritionalBenefits(); }

    @GetMapping("/allCuisines")
    public Collection<Cuisine> getAllCuisines(){ return recipeService.findAllCuisines();}

    @GetMapping("/allRecipeStatuses")
    public Collection<RecipeStatus> getAllRecipeStatuses(){ return recipeService.findAllRecipeStatuses();}

    @GetMapping("/allDietaryCategories")
    public Collection<DietaryCategory> getAllDietaryCategories(){ return recipeService.findAllDietaryCategories(); }


    @PostMapping("/UploadRecipeImage/{recipeID}")
    public void handleFileUpload(@PathVariable("recipeID") int recipeID, @RequestParam("file") MultipartFile file) {

        log.info("got here UploadRecipeImage");
        try{
            recipeService.addImageToRecipe(recipeID, file);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @PostMapping(value = "/UploadRecipeImages/{recipeID}", consumes="multipart/form-data")
    public void handleMultipfileUpload(@PathVariable("recipeID") int recipeID, @RequestParam("file") MultipartFile[] file,
                                RedirectAttributes redirectAttributes) throws Exception {

        log.info("got here UploadRecipeImage: " + file);
        log.info("got here UploadRecipeImage: " + file.length);

            for(MultipartFile uploadedFile : file) {
                recipeService.addImageToRecipe(recipeID, uploadedFile);
            }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded recipe image files!");

    }


//    private RecipeDto convertToDto(Recipe recipe) {
//        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
//        return recipeDto;
//    }
//
//    private Recipe convertToEntity(RecipeDto recipeDto) throws ParseException {
//        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
//
//        return recipe;
//    }


}