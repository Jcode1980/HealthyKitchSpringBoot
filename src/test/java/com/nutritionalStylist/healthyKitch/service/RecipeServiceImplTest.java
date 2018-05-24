package com.nutritionalStylist.healthyKitch.service;

import com.google.common.base.Predicate;
import com.nutritionalStylist.healthyKitch.Application;
import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.not;
import static springfox.documentation.builders.PathSelectors.regex;

//To do autowired. u must run these two lines
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RecipeServiceImplTest {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeService recipeService;

    @Test
    public void findMealTypes() {

    }

    @Test
    public void findRecipeByID() {
        System.out.println("recipeRepository :" + recipeRepository);
        System.out.println("NOt null?? " + (recipeRepository.findById(1)));
        assertThat(recipeRepository.findById(1).get().getName(), is("Test"));
    }

    @Test
    public void getRecipeUsingSearchDTOTest(){
        RecipeSearchDto recipeSearchDTO = new RecipeSearchDto();
        ArrayList<String> searchStrings = new ArrayList<>();
        searchStrings.add("Test");
        searchStrings.add("st");

        ArrayList<Integer> mealTypes = new ArrayList<>();
        mealTypes.add(new Integer(1));


        recipeSearchDTO.setSearchStrings(searchStrings);
        recipeSearchDTO.setMealTypesID(mealTypes);
        recipeSearchDTO.setNutritionalBenefitID(mealTypes);
        recipeSearchDTO.setDietaryRequirementsID(mealTypes);
        recipeSearchDTO.setCuisinesID(mealTypes);
        List<Recipe> recipes  = recipeRepository.getRecipeUsingSearchDTO(recipeSearchDTO);
        assertThat(recipes.size(), is(1));
    }

    @Test
    public void getRecipeUsingSearchDTO_TestTrendingFlag_Should_Return_Six_Recipes(){
        RecipeSearchDto recipeSearchDTO = new RecipeSearchDto();
        recipeSearchDTO.setSearchForTrending(true);
        List<Recipe> recipes  = recipeRepository.getRecipeUsingSearchDTO(recipeSearchDTO);
        assertThat(recipes.size(), is(6));
    }

    @Test
    public void saveRecipe() {
    }

    @Test
    public void findAllRecipes() {
    }

    @Test
    public void regexTest(){
        Predicate<String> regexER =  regex("api.*|/login|/logout");
        assertThat(regexER.apply("api/"),is(true));
        assertThat(regexER.apply("/login"),is(true));
    }


    @Test
    public void addImageToRecipe(){
        try{
            String testFile = "/Users/johnadolfo/Desktop/test.png";
            MockMultipartFile multipartFile = new MockMultipartFile("files", "Test.png",
                    "text/plain",  new FileInputStream(testFile));

            recipeService.addImageToRecipe(1, multipartFile);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}