package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.RecipeStatus;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RecipeStatusRepository extends PagingAndSortingRepository<RecipeStatus,Integer> {
}
