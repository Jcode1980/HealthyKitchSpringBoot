package com.nutritionalStylist.healthyKitch.repository;

import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
    private Logger log = Logger.getLogger(RecipeRepositoryImpl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Recipe> getRecipeUsingSearchDTO(RecipeSearchDto searchDto) {
        String searchQuery = searchDto.isSearchForTrending() ? queryForTrendingRecipes() : standardQuerySearch(searchDto);

        Query query = entityManager.createNativeQuery(searchQuery.toString(), Recipe.class);
        return query.getResultList();


    }

//    public void baseGetRecipeUsingSearchDTO(RecipeSearchDto searchDto, Integer pageNumber, Integer pageSize){
//        String searchQuery = searchDto.isSearchForTrending() ? queryForTrendingRecipes() : standardQuerySearch(searchDto);
//
//        Query query = entityManager.createNativeQuery(searchQuery.toString(), Recipe.class);
//        query.setFirstResult();
//        //return query.getResultList();
//
//        Query query = entityManager.createQuery("From Foo");
//        int pageNumber = 1;
//        int pageSize = 10;
//        query.setFirstResult((pageNumber-1) * pageSize);
//        query.setMaxResults(pageSize);
//
//        List <Foo> fooList = query.getResultList();
//
//
//        Query queryTotal = entityManager.createQuery
//                ("Select count(f.id) from Foo f");
//        long countResult = (long)queryTotal.getSingleResult();
//
//
//        int pageSize = 10;
//        int pageNumber = (int) ((countResult / pageSize) + 1);
//    }

    private String standardQuerySearch(RecipeSearchDto searchDto){
        StringBuilder searchQuery= new StringBuilder("Select r.* FROM recipe as r " );

        searchQuery.append(createJoinPartOfQuery(searchDto));

        searchQuery.append(createWherePartOfQuery(searchDto));

        log.info("goat here getRecipeUsingSearchDTO " +  searchQuery.toString());
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
        String searchString = searchStrings.stream().filter(s->(s!=null && s.length() > 0)).map(s->"r.name like '%"+s+"%'").collect(Collectors.joining(" or "));
        //searchString = searchString.substring(0, searchString.lastIndexOf(" and "));
        log.info("returning search String : " + searchString);
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
        boolean isFirstCriteria = true;
        if(recipeSearchDto.hasSearchStrings()) {
            whereClauseStringBuffer.append(queryQualsForSearchStrings(recipeSearchDto.getSearchStrings()));
            isFirstCriteria = false;
        }

        if(recipeSearchDto.hasMealTypesSearch()){

//            if(!isFirstCriteria) {
//                whereClauseStringBuffer.append(" and ");
//            }
//            else{
//                isFirstCriteria = false;
//            }
            isFirstCriteria = appendAndString(whereClauseStringBuffer, isFirstCriteria);
            String mealTypeSearchString = " m.ID in (" +recipeSearchDto.getMealTypesID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")\n";
            whereClauseStringBuffer.append(mealTypeSearchString);
        }

        if(recipeSearchDto.hasNutritionalBenefitSearch()){
            if(!isFirstCriteria)
//            {
//                whereClauseStringBuffer.append(" and ");
//            }
//            else{
//                isFirstCriteria = false;
//            }
            isFirstCriteria = appendAndString(whereClauseStringBuffer, isFirstCriteria);
            String mealTypeSearchString = " n.ID in (" +recipeSearchDto.getNutritionalBenefitID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")\n";
            whereClauseStringBuffer.append(mealTypeSearchString);
        }

        if(recipeSearchDto.hasDietaryRequirementSearch()){
//            if(!isFirstCriteria)
//            {
//                whereClauseStringBuffer.append(" and ");
//            }
//            else{
//                isFirstCriteria = false;
//            }
            isFirstCriteria = appendAndString(whereClauseStringBuffer, isFirstCriteria);
            String dietarySearchString = " dc.ID in (" +recipeSearchDto.getDietaryRequirementsID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")\n";
            whereClauseStringBuffer.append(dietarySearchString);

        }

        if(recipeSearchDto.hasCuisineSearch()){
//            if(!isFirstCriteria)
//            {
//                whereClauseStringBuffer.append(" and ");
//            }
//            else{
//                isFirstCriteria = false;
//            }
            isFirstCriteria = appendAndString(whereClauseStringBuffer, isFirstCriteria);
            String cuisineSearchString = " c.ID in (" +recipeSearchDto.getCuisinesID().stream().map(i -> String.valueOf(i)).collect(Collectors.joining(", ")) + ")\n";
            whereClauseStringBuffer.append(cuisineSearchString);

        }

        if(recipeSearchDto.getCreatedByUserID() != null){
            isFirstCriteria = appendAndString(whereClauseStringBuffer, isFirstCriteria);
            String createdBySearchString = " r.created_byid = " +  recipeSearchDto.getCreatedByUserID();
            whereClauseStringBuffer.append(createdBySearchString);
        }


        isFirstCriteria = appendAndString(whereClauseStringBuffer, isFirstCriteria);
        whereClauseStringBuffer.append(" r.deleted is null");



        return whereClauseStringBuffer.toString();

    }

    private boolean appendAndString(StringBuilder theQuery, boolean isFirstCriteria){
        if(!isFirstCriteria)
        {
            theQuery.append(" and ");
            return true;
        }
        else{
            return false;
        }
    }

    //TODO: Figure out logic to this.. but for now it returns the newest recipes.
    private String queryForTrendingRecipes(){
        StringBuilder query = new StringBuilder("Select r.* FROM recipe as r where r.deleted is null\n" );
        query.append("order by created desc\n");
        query.append("LIMIT 6;");

        return query.toString();
    }


}
