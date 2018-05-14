package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Recipe> getRecipeUsingSearchDTO(RecipeSearchDto searchDto) {
        StringBuilder searchQuery = new StringBuilder("Select r.* FROM recipe as r " );

        searchQuery.append(createJoinPartOfQuery(searchDto));
        searchQuery.append(createWherePartOfQuery(searchDto));

        System.out.println("goat here getRecipeUsingSearchDTO " +  searchQuery.toString());
//        Query query = entityManager.createNativeQuery("SELECT em.* FROM spring_data_jpa_example.employee as em " +
//                "WHERE em.firstname LIKE ?", Employee.class);
//        query.setParameter(1, firstName + "%");

//        Query query = entityManager.createNativeQuery(
//                "Select r.* FROM recipe as r " +
//                "Where r.name LIKE ?", Recipe.class);
//        query.setParameter(1, "Test" + "%");


        Query query = entityManager.createNativeQuery(searchQuery.toString(), Recipe.class);
        return query.getResultList();

    }

    private String queryQualsForSearchStrings(Collection<String> searchStrings){
        String searchString = searchStrings.stream().map(s->"name like '%"+s+"%'").collect(Collectors.joining(" and "));
        //searchString = searchString.substring(0, searchString.lastIndexOf(" and "));
        System.out.println("returning search String : " + searchString);
        return searchString;

    }

    private String createJoinPartOfQuery(RecipeSearchDto recipeSearchDto){
        StringBuilder joinTableStringBuffer = new StringBuilder(" where ");
        if(recipeSearchDto.hasMealTypesSearch()){
            joinTableStringBuffer.append("left join meal_type_recipe rtc on (r.id = rtc.recipeID) " +
                    "left join meal_type m on (rtc.mealTypeID on m.ID)");
        }

        return joinTableStringBuffer.toString();
    }

    private String createWherePartOfQuery(RecipeSearchDto recipeSearchDto){
        StringBuilder whereClauseStringBuffer = new StringBuilder(" where ");

        whereClauseStringBuffer.append(queryQualsForSearchStrings(recipeSearchDto.getSearchStrings()));

        if(recipeSearchDto.hasMealTypesSearch()){
            String mealTypeSearchString = "m.ID in (" +recipeSearchDto.getMealTypesID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")";
            whereClauseStringBuffer.append(mealTypeSearchString);
        }

        return whereClauseStringBuffer.toString();

    }



}
