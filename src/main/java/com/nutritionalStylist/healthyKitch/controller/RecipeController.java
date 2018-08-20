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


    @GetMapping("/allRecipes")
    public Collection<RecipeDto> getAllRecipes(){
        System.out.println("got to all recipes");
        return recipeService.findAllRecipes().stream().map(recipe -> RecipeDto.convertToDto(recipe)).
                collect(Collectors.toList());
    }

    @JsonView(Views.ListView.class)
    @GetMapping("/recipes")
    public Collection<RecipeDto> searchRecipesByDTO(RecipeSearchDto searchDto) {
        Collection<Recipe> recipes = recipeService.findRecipesUsingRecipeDTO(searchDto);
        System.out.println("found recipes : " + recipes);
        return recipes.stream().map(recipe -> RecipeDto.convertToDto(recipe)).collect(Collectors.toList());
    }

    @JsonView(Views.DetailedView.class)
    @GetMapping(value = "/{recipeID}")
    public RecipeDto getRecipeById(@PathVariable("recipeID") int recipeID) {
        Recipe recipe = recipeService.findRecipeByID(recipeID).orElseThrow(IllegalArgumentException::new);
        System.out.println("how many ingredients??" + recipe.getMeasuredIngredients().size());
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
        //System.out.println("user is : " + user);
        return recipeService.findAllMealTypes(); }


    @GetMapping("/allMetrics")
    public Collection<Metric> getAllMetrics(){ return recipeService.findAllMetrics(); }


    @GetMapping("/allNutritionalBenefits")
    public Collection<NutritionalBenefit> getAllNutritionalBenefit(){ return recipeService.findAllNutritionalBenefits(); }

    @GetMapping("/allCuisines")
    public Collection<Cuisine> getAllCuisines(){ return recipeService.findAllCuisines();}

    @GetMapping("/allDietaryCategories")
    public Collection<DietaryCategory> getAllDietaryCategories(){ return recipeService.findAllDietaryCategories(); }



    @PostMapping("/UploadRecipeImage/{recipeID}")
    public String handleFileUpload(@PathVariable("recipeID") int recipeID, @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        System.out.println("got here UploadRecipeImage");
        try{
            recipeService.addImageToRecipe(recipeID, file);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }


    @PostMapping(value = "/UploadRecipeImages/{recipeID}", consumes="multipart/form-data")
    public String handleMultipfileUpload(@PathVariable("recipeID") int recipeID, @RequestParam("file") MultipartFile[] file,
                                RedirectAttributes redirectAttributes) throws IOException {

        System.out.println("got here UploadRecipeImage: " + file);
        System.out.println("got here UploadRecipeImage: " + file.length);
        try{
            for(MultipartFile uploadedFile : file) {
                recipeService.addImageToRecipe(recipeID, uploadedFile);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded recipe image files!");

        return "redirect:/";
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