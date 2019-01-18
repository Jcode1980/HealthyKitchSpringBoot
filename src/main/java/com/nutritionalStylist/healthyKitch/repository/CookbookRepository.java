package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Cookbook;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CookbookRepository extends PagingAndSortingRepository<Cookbook,Integer>  {

}
