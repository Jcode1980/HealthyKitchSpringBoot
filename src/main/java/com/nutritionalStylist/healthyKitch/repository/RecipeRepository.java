package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.Collection;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe,Integer>{

    ArrayList<Recipe> findByMealTypesIn(Collection<MealType> mealTypes);

}
