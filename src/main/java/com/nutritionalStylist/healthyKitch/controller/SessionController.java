package com.nutritionalStylist.healthyKitch.controller;

import com.nutritionalStylist.healthyKitch.exception.ResourceNotFoundException;
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/session/recipe")
public class SessionController {
    @PersistenceContext
    private EntityManager entityManager;

    private final RecipeService recipeService;
    private final StorageService storageService;
    @Autowired
    public SessionController(RecipeService recipeService, StorageService storageService) {
        this.storageService = storageService;
        this.recipeService = recipeService;
    }

    /**
     * Update Recipe
     */
    @PutMapping(value = "/{recipeID}")
    @ResponseStatus(HttpStatus.OK)
    public void updateRecipe(@Valid @RequestBody RecipeDto recipeDto ) {
        Recipe recipe;
        //TODO: investigate proper way of handling exceptions.
        try{
            Optional<Recipe> foundRecipeOpt = recipeService.findRecipeByID(recipeDto.getId());
            Recipe foundRecipe = foundRecipeOpt.get();
            recipe = RecipeDto.convertToEntity(recipeDto);

            System.out.println("ready in mins?? " + recipeDto.getReadyInMins());
            System.out.println("how many ingredients: " + recipeDto.getMeasuredIngredients().toArray().length);
            if(recipeDto.getMeasuredIngredients().toArray().length > 0) {

                System.out.println("thisssss is the recipe from mappedModel DTO : " + ((MeasuredIngredient) recipeDto.getMeasuredIngredients().toArray()[0]).metric());
            }
            else{
                System.out.println("No ingredients found");
            }
        }catch (Exception e){
            System.out.println("something went wrong when converting dto to recipe");
            e.printStackTrace();
            return;
        }

        //System.out.println("thisssss is the recipe from mappedModel : " + ((MeasuredIngredient)recipe.getMeasuredIngredients().toArray()[0]).metric());

        this.recipeService.updateRecipe(recipe);
        //this.recipeService.saveRecipe(recipe);

//        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
//        recipeModel.get().setName(recipeRequest.getName());
//
//        this.recipeService.saveRecipe(recipeModel.get());
//        return recipeModel.get();
    }



    /**
     * Create Recipe
     */
    @PostMapping(value = "/createRecipe")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Integer createRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe recipe;
        //TODO: investigate proper way of handling exceptions.

//        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String name = user.getUsername(); //get logged in username
//        System.out.println("Person logged in is: " + user.getFullName() );

        System.out.println("this is the recipe DTO from measured Ingredient : " + recipeDto.getMeasuredIngredients());
        System.out.println("this is the recipe DTO from Cusines : " + recipeDto.getCuisines());
        System.out.println("this is the recipe DTO from Dietary Category : " + recipeDto.getDietaryCategories());

        try{
            recipe = RecipeDto.convertToEntity(recipeDto);
        }catch (Exception e){
            System.out.println("something went wrong when converting dto to recipe");
            return null;
        }

        System.out.println("this is the recipe from mappedModel : " + recipe);
        //System.out.println("this is the instructions from mappedModel : " + recipe.getInstructions().size());
        //System.out.println("this is the measuredIngredients : " + recipe.getMeasuredIngredients().size());
        //List<NutritionalBenefit> nutritionBenefits = new ArrayList<>(recipe.getNutritionalBenefits());
        //nutritionBenefits.stream().forEach(nutritionalBenefit -> {entityManager.persist(nutritionalBenefit);});

        recipeService.saveRecipe(recipe);
        return recipe.getId();


        //this.recipeService.saveRecipe(recipe);

//        // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
//        recipeModel.get().setName(recipeRequest.getName());
//
//        this.recipeService.saveRecipe(recipeModel.get());
//        return recipeModel.get();
    }



    /*
     {
        "comment": "string",
        "id": 0,
        "new": true,
        "rating": 0,
        "user": {
            "id"
        }
    }

     */


    /**
     * Post Review
     */
    @PostMapping(value = "/review/{recipeID}")
    @ResponseStatus(HttpStatus.OK)
    public void createReview(@PathVariable("recipeID") Integer recipeID, @Valid @RequestBody RecipeReview recipeReview) throws Exception {

        recipeService.addReviewForRecipe(recipeID, recipeReview);

        System.out.println("this is the recipeReview: " + recipeReview);
        System.out.println("this is the recipeReviews user: " + recipeReview.getUser().getFullName());



    }

    /**
     * Update Review
     */
    @PutMapping(value = "/review/{reviewID}")
    @ResponseStatus(HttpStatus.OK)
    public void updateReview(@PathVariable("reviewID") Integer reviewID, @Valid @RequestBody RecipeReview recipeReviewDTO){
        recipeService.updateReview(recipeReviewDTO);
    }



}
