package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Service
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository){
        this.recipeRepository  = recipeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<MealType> findMealTypes() throws DataAccessException {
        return null;
    }

    @Override
    public Recipe findRecipeByID(int id) {

        return recipeRepository.findOne(id);
    }


    @Override
    public void saveRecipe(Recipe recipe) {

    }

    @Override
    public Collection<Recipe> findAllRecipes() {
        return (Collection<Recipe>) recipeRepository.findAll();
    }

    @Override
    public void addImageToRecipe(MultipartFile file) {

    }
}
