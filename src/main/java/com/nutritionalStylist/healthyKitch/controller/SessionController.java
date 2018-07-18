package com.nutritionalStylist.healthyKitch.controller;

import com.nutritionalStylist.healthyKitch.model.NutritionalBenefit;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.service.RecipeService;
import com.nutritionalStylist.healthyKitch.service.StorageService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/session/")
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
    public void updateRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        Recipe recipe;
        //TODO: investigate proper way of handling exceptions.
        try{
            recipe = RecipeDto.convertToEntity(recipeDto);
        }catch (Exception e){
            System.out.println("something went wrong when converting dto to recipe");
            return;
        }

        System.out.println("thisssss is the recipe from mappedModel : " + recipe);

        this.recipeService.saveRecipe(recipe);

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
//        System.out.println("this is the instructions from mappedModel : " + recipe.getInstructions().size());
//        System.out.println("this is the measuredIngredients : " + recipe.getMeasuredIngredients().size());
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
}
