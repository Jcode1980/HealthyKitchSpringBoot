package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Recipe> getRecipeUsingSearchDTO(RecipeSearchDto searchDto) {
        
        System.out.println("goat here getRecipeUsingSearchDTO");
//        Query query = entityManager.createNativeQuery("SELECT em.* FROM spring_data_jpa_example.employee as em " +
//                "WHERE em.firstname LIKE ?", Employee.class);
//        query.setParameter(1, firstName + "%");
        Query query = entityManager.createNativeQuery(
                "Select r.* FROM recipe as r " +
                "Where r.name LIKE ?", Recipe.class);
        query.setParameter(1, searchDto.getSearchString() + "%");

        return query.getResultList();

    }
}
