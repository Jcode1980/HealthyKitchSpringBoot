package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;

import java.util.List;

public interface RecipeRepositoryCustom {
    List<Recipe> getRecipeUsingSearchDTO(RecipeSearchDto searchDto);
    //void getRecipesUsingPagination();
}
