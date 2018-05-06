package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;


public interface RecipeService {
    Collection<MealType> findMealTypes();

    Recipe findRecipeByID(int id);

    void saveRecipe(Recipe recipe);

    Collection<Recipe> findAllRecipes();

    void addImageToRecipe(MultipartFile file);

}
