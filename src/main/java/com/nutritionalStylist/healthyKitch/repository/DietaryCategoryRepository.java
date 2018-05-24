package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.DietaryCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DietaryCategoryRepository extends PagingAndSortingRepository<DietaryCategory,Integer> {
}
