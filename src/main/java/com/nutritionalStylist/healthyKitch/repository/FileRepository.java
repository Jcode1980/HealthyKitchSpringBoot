package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.File;
import com.nutritionalStylist.healthyKitch.model.MealType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FileRepository extends PagingAndSortingRepository<File,Integer> {
}
