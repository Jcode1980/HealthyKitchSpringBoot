package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.RecipeImage;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeImageRepository extends PagingAndSortingRepository<RecipeImage,Integer> {
}
