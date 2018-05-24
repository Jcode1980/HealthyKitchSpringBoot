package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeRepositoryImpl implements RecipeRepositoryCustom {



    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Recipe> getRecipeUsingSearchDTO(RecipeSearchDto searchDto) {
        String searchQuery = searchDto.isSearchForTrending() ? queryForTrendingRecipes() : standardQuerySearch(searchDto);

        Query query = entityManager.createNativeQuery(searchQuery.toString(), Recipe.class);
        return query.getResultList();

    }

    private String standardQuerySearch(RecipeSearchDto searchDto){
        StringBuilder searchQuery= new StringBuilder("Select r.* FROM recipe as r " );

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

        return searchQuery.toString();
    }


    private String queryQualsForSearchStrings(Collection<String> searchStrings){
        String searchString = searchStrings.stream().map(s->"r.name like '%"+s+"%'").collect(Collectors.joining(" or "));
        //searchString = searchString.substring(0, searchString.lastIndexOf(" and "));
        System.out.println("returning search String : " + searchString);
        return searchString;

    }

    private String createJoinPartOfQuery(RecipeSearchDto recipeSearchDto){
        StringBuilder joinTableStringBuffer = new StringBuilder("");

        if(recipeSearchDto.hasMealTypesSearch()){
            joinTableStringBuffer.append("left join meal_type_recipe rtc on (r.id = rtc.recipeID) " +
                    "left join meal_type m on (rtc.meal_typeID = m.ID)\n");
        }

        if(recipeSearchDto.hasNutritionalBenefitSearch()){
            joinTableStringBuffer.append("left join nutritional_benefit_recipe nbr on (r.id = nbr.recipeID) " +
                    "left join nutritional_benefit n on (nbr.nutritional_benefitid = n.ID)\n");
        }

        if(recipeSearchDto.hasDietaryRequirementSearch()){
            joinTableStringBuffer.append("left join dietary_category_recipe dcr on (r.id = dcr.recipeID) " +
                    "left join dietary_category dc on (dcr.dietary_categoryid = dc.ID)\n");
        }

        if(recipeSearchDto.hasCuisineSearch()){
            joinTableStringBuffer.append("left join cuisine_recipe cr on (r.id = cr.recipeID) " +
                    "left join cuisine c on (cr.cuisineid = c.ID)\n");
        }



        return joinTableStringBuffer.toString();
    }

    private String createWherePartOfQuery(RecipeSearchDto recipeSearchDto){
        StringBuilder whereClauseStringBuffer = new StringBuilder(" where ");

        whereClauseStringBuffer.append(queryQualsForSearchStrings(recipeSearchDto.getSearchStrings()));

        if(recipeSearchDto.hasMealTypesSearch()){
            String mealTypeSearchString = " and m.ID in (" +recipeSearchDto.getMealTypesID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")\n";
            whereClauseStringBuffer.append(mealTypeSearchString);
        }

        if(recipeSearchDto.hasNutritionalBenefitSearch()){
            String mealTypeSearchString = " and n.ID in (" +recipeSearchDto.getNutritionalBenefitID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")\n";
            whereClauseStringBuffer.append(mealTypeSearchString);
        }

        if(recipeSearchDto.hasDietaryRequirementSearch()){
            String dietarySearchString = " and dc.ID in (" +recipeSearchDto.getDietaryRequirementsID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")\n";
            whereClauseStringBuffer.append(dietarySearchString);

        }

        if(recipeSearchDto.hasCuisineSearch()){
            String cuisineSearchString = " and c.ID in (" +recipeSearchDto.getCuisinesID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")\n";
            whereClauseStringBuffer.append(cuisineSearchString);

        }


        return whereClauseStringBuffer.toString();

    }

    //TODO: Figure out logic to this.. but for now it returns the newest recipes.
    private String queryForTrendingRecipes(){
        StringBuilder query = new StringBuilder("Select r.* FROM recipe as r\n" );
        query.append("order by created desc\n");
        query.append("LIMIT 6;");

        return query.toString();
    }


}
