package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.NutritionalBenefit;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.repository.MealTypeRepository;
import com.nutritionalStylist.healthyKitch.repository.NutritionalBenefitRepository;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;
    private MealTypeRepository mealTypeRepository;
    private NutritionalBenefitRepository nutritionalBenefitRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, MealTypeRepository mealTypeRepository,
                             NutritionalBenefitRepository nutritionalBenefitRepository){
        this.recipeRepository  = recipeRepository;
        this.mealTypeRepository  = mealTypeRepository;
        this.nutritionalBenefitRepository  = nutritionalBenefitRepository;
    }


    @Override
    public Optional<Recipe> findRecipeByID(int id) {
        return recipeRepository.findById(id);
    }


    @Override
    public void saveRecipe(Recipe recipe) {

    }

    @Override
    public Collection<Recipe> findRecipesUsingRecipeDTO(RecipeSearchDto searchDto) {

        //Collection<MealType> mealTypes;
        //String searchString = searchDto.getSearchString();

        return recipeRepository.getRecipeUsingSearchDTO(searchDto);
//
//        if(recipeDto.getMealTypesID() != null) {
//            Collection<Integer> mealTypesID =  new ArrayList(recipeDto.getMealTypesID()) ;
//            mealTypes = makeCollection(mealTypeRepository.findAllById(mealTypesID));
//        }else{
//            mealTypes = Collections.EMPTY_LIST;
//        }
//        System.out.println("these are the meal Types: "+ mealTypes);
//        System.out.println("this is the search String: " + searchString);
//
//        if(mealTypes.size() == 0){
//            return recipeRepository.findByNameLike(recipeDto.getSearchString());
//        }
//        else{
//            return recipeRepository.findByNameLikeAndMealTypesIn(recipeDto.getSearchString(), mealTypes);
//        }
    }

    @Override
    public Collection<Recipe> findAllRecipes() {
        return (Collection<Recipe>) recipeRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<MealType> findAllMealTypes() throws DataAccessException { return (Collection<MealType>) mealTypeRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Collection<NutritionalBenefit> findAllNutritionalBenefits() throws DataAccessException { return (Collection<NutritionalBenefit>) nutritionalBenefitRepository.findAll(); }



    @Override
    public void addImageToRecipe(MultipartFile file) {

    }

    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }



}
