package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.Application;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.not;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RecipeServiceImplTest {
    @Autowired
    private RecipeRepository recipeRepository;


    @Test
    public void findMealTypes() {

    }

    @Test
    public void findRecipeByID() {
        System.out.println("recipeRepository :" + recipeRepository);
        System.out.println("NOt null?? " + (recipeRepository.findOne(1)));
        assertThat(recipeRepository.findOne(1).getName(), is("test"));
    }

    @Test
    public void saveRecipe() {
    }

    @Test
    public void findAllRecipes() {
    }



}