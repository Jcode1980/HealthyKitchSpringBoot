package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.exception.ResourceNotFoundException;
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;


public interface RecipeService {
    Collection<MealType> findAllMealTypes();

    Collection<NutritionalBenefit> findAllNutritionalBenefits();

    Collection<Cuisine> findAllCuisines();

    Collection<DietaryCategory> findAllDietaryCategories();

    Optional<Recipe> findRecipeByID(int id);

    void saveRecipe(Recipe recipe);

    Collection<Recipe> findRecipesUsingRecipeDTO(RecipeSearchDto searchDto);


    Collection<Metric> findAllMetrics();

    void addImageToRecipe(int recipeID, MultipartFile file) throws Exception;

    void addImageToMealType(int mealTypeID, MultipartFile file) throws Exception;

    Collection<RecipeReview> reviewsForRecipe(int recipeID) throws Exception;

    RecipeReview addReviewForRecipe(Integer recipeID, RecipeReview review) throws Exception;

    void updateReview(RecipeReview reviewDTO) throws ResourceNotFoundException;

    void updateRecipe(Recipe recipeDto) throws ResourceNotFoundException;

    void deleteRecipe(int recipeID) throws Exception;
}
