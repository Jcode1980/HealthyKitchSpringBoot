package com.nutritionalStylist.healthyKitch.service;

import com.google.common.base.Predicate;
import com.nutritionalStylist.healthyKitch.Application;
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeSearchDto;
import com.nutritionalStylist.healthyKitch.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.util.*;

import static org.hamcrest.Matchers.greaterThan;
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
        Collection<MealType> mealTypes = recipeService.findAllMealTypes();
        assertThat(mealTypes.size(), greaterThan(0));
    }

    @Test
    public void findAllNutritionalBenefits() {
        Collection<NutritionalBenefit> nutritionalBenefits = recipeService.findAllNutritionalBenefits();
        assertThat(nutritionalBenefits.size(), greaterThan(0));
    }

    @Test
    public void findAllDietaryCategories() {
        Collection<DietaryCategory> dietaryCategories = recipeService.findAllDietaryCategories();
        assertThat(dietaryCategories.size(), greaterThan(0));
    }

    @Test
    public void findAllMetrics() {
        Collection<Metric> metrics = recipeService.findAllMetrics();
        assertThat(metrics.size(), greaterThan(0));
    }


    @Test
    public void findAll() {
        Collection<Cuisine> cuisines = recipeService.findAllCuisines();
        assertThat(cuisines.size(), greaterThan(0));
    }

    @Test
    public void findRecipeByID() {
        //System.out.println("recipeRepository :" + recipeRepository);
        //System.out.println("NOt null?? " + (recipeRepository.findById(1)));
        assertThat(recipeRepository.findById(1).get().getId(), is(1));
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
        assertThat(recipes.size(), greaterThan(0));
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

//    @Test
//    public void regexTest(){
//        Predicate<String> regexER =  regex("api.*|/login|/logout");
//        assertThat(regexER.apply("api/"),is(true));
//        assertThat(regexER.apply("/login"),is(true));
//    }


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


    @Test
    public void removeDietaryCartegoryTest(){
        Optional<Recipe> recipeOpt = recipeRepository.findById(1);
        Recipe recipe = recipeOpt.get();
        Set<DietaryCategory> dietaryCategories  = recipe.getDietaryCategories();

        System.out.println("found dietary categories : " + dietaryCategories.size());
        System.out.println("found dietary categories objects : " + dietaryCategories);

        for(DietaryCategory dietaryCategory : dietaryCategories){
            System.out.println("found category: " + dietaryCategory.getId() + " " + dietaryCategory.getName());
        }

        ArrayList<DietaryCategory> dietaryCategoriesList = new ArrayList<>();
        dietaryCategoriesList.addAll(dietaryCategories);
        dietaryCategoriesList.remove(dietaryCategoriesList.get(0));

        System.out.println("numbers in List: " + dietaryCategoriesList.size());

        Set<DietaryCategory> lSet = new HashSet<DietaryCategory>();
        lSet.addAll(dietaryCategoriesList);

        System.out.println("numbers in set: " + lSet.size());

        recipe.setDietaryCategories(lSet);
        recipeService.saveRecipe(recipe);
        assertThat(recipe.getDietaryCategories().size(), is(1));
    }
}