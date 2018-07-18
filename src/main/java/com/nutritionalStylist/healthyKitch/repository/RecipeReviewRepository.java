package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.RecipeReview;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecipeReviewRepository extends PagingAndSortingRepository<RecipeReview,Integer> {

    List<RecipeReview> findByRecipeOrderByCreatedDateDesc(Recipe recipe);
}
