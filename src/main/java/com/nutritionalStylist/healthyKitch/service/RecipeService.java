package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.NutritionalBenefit;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;


public interface RecipeService {
    Collection<MealType> findAllMealTypes();

    Collection<NutritionalBenefit> findAllNutritionalBenefits();

    Optional<Recipe> findRecipeByID(int id);

    void saveRecipe(Recipe recipe);

    Collection<Recipe> findRecipesUsingRecipeDTO(RecipeSearchDto searchDto);

    Collection<Recipe> findAllRecipes();

    void addImageToRecipe(MultipartFile file);

}
