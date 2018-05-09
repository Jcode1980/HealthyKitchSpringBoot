package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.MealType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MealTypeRepository extends PagingAndSortingRepository<MealType,Integer> {
}
