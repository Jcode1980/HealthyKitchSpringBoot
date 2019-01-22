package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.Cookbook;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.repository.CookbookRepository;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CookbookServiceImpl implements CookbookService {
    @PersistenceContext
    private EntityManager entityManager;

    private CookbookRepository cookbookRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    public CookbookServiceImpl(CookbookRepository cookbookRepository, RecipeRepository recipeRepository){
        this.cookbookRepository = cookbookRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Cookbook createCookbookForUser(User user) {
        Cookbook newCookBook = new Cookbook(user);
        cookbookRepository.save(newCookBook);

        return newCookBook;
    }

    @Override
    public void saveCookbook(Cookbook cookbook) {
        entityManager.merge(cookbook);
    }

    @Override
    public void addRecipeToCookbook(Integer cookbookID, Integer recipeID) throws IllegalArgumentException {
        Optional<Cookbook> cookbook = cookbookRepository.findById(cookbookID);
        Optional<Recipe> recipe = recipeRepository.findById(recipeID);

        if(!(cookbook.isPresent() || recipe.isPresent())){
            throw new IllegalArgumentException("Incorrect Id passed in. Can not find either cookbook or recipe");
        }
    }

    @Override
    public List<Cookbook> cookbooksForUser(User user){
        //FixME i need to get only the users cookbook
        return StreamSupport.stream(cookbookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cookbook> findCookbookById(Integer id){
        return cookbookRepository.findById(id);
    }

}
