package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Metric;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MetricRepository extends PagingAndSortingRepository<Metric, Integer> {
}
