package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Collection;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe,Integer>{

    ArrayList<Recipe> findByNameLike(String name);
    ArrayList<Recipe> findByNameLikeAndMealTypesIn(String name, Collection<MealType> mealTypes);


}
