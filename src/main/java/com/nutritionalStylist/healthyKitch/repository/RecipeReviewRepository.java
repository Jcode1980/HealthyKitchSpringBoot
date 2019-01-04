package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.RecipeReview;
import com.nutritionalStylist.healthyKitch.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.Query;
import java.util.List;

public interface RecipeReviewRepository extends PagingAndSortingRepository<RecipeReview,Integer> {

    List<RecipeReview> findByRecipeOrderByCreatedDateDesc(Recipe recipe);

    List<RecipeReview> findByRecipeAndUserOrderByCreatedDateDesc(Recipe recipe, User user);


}
