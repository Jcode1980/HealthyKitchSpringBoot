package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Cuisine;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CuisineRepository extends PagingAndSortingRepository<Cuisine,Integer> {

}
