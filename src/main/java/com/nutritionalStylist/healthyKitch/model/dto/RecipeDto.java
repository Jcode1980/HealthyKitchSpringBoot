package com.nutritionalStylist.healthyKitch.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeDto {
   // private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static private ModelMapper MODEL_MAPPER;
    @JsonView({Views.ListView.class})
    private Integer id;
    @JsonView({Views.ListView.class})
    private String name;
    @JsonView({Views.ListView.class})
    private Integer defaultImageID;
    @JsonView({Views.DetailedView.class})
    private List<Instruction> instructions;
    @JsonView({Views.DetailedView.class})
    private Collection<MealType> mealTypes;
    @JsonView({Views.DetailedView.class})
    private Collection<MeasuredIngredient> measuredIngredients;
    @JsonView({Views.DetailedView.class})
    @JsonIgnoreProperties({"name"})
    private Collection<DietaryCategory> dietaryCategories;
    @JsonView({Views.DetailedView.class})
    private Collection<Cuisine> cuisines;
    @JsonView({Views.DetailedView.class})
    private Collection<NutritionalBenefit> nutritionalBenefits;


    static{
        MODEL_MAPPER = new ModelMapper();
        PropertyMap<Recipe, RecipeDto> recipeMap = new PropertyMap<Recipe, RecipeDto>() {
        protected void configure() {
            //map(source.getDefaultImage(, destination.getDefaultImageID());
            //map(source.getMeasuredIngredients(), destination.getMeasuredIngredients());
            //map(source.getInstructions(), destination.getInstructions());
            //map(source.sortedDietaryCategories(), destination.getDietaryCategories());
            //map(source.sortedCuisines(), destination.getCuisines());
            }
        };

        MODEL_MAPPER.addMappings(recipeMap);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Collection<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(Collection<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public Collection<MeasuredIngredient> getMeasuredIngredients() {
        return measuredIngredients;
    }

    public void setMeasuredIngredients(Collection<MeasuredIngredient> measuredIngredients) {
        this.measuredIngredients = measuredIngredients;
    }

    public Collection<DietaryCategory> getDietaryCategories() {
        return dietaryCategories;
    }

    public void setDietaryCategories(Collection<DietaryCategory> dietaryCategories) {
        this.dietaryCategories = dietaryCategories;
    }

    public Collection<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(Collection<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public Collection<NutritionalBenefit> getNutritionalBenefits() {
        return nutritionalBenefits;
    }

    public void setNutritionalBenefits(Collection<NutritionalBenefit> nutritionalBenefits) {
        this.nutritionalBenefits = nutritionalBenefits;
    }

    public Integer getDefaultImageID() {
        return defaultImageID;
    }

    public void setDefaultImageID(Integer defaultImageID) {
        this.defaultImageID = defaultImageID;
    }


    static public RecipeDto convertToDto(Recipe recipe) {
        RecipeDto recipeDto = MODEL_MAPPER.map(recipe, RecipeDto.class);
        return recipeDto;
    }

    static public Recipe convertToEntity(RecipeDto recipeDto) throws ParseException {
        Recipe recipe = MODEL_MAPPER.map(recipeDto, Recipe.class);

        //Enrich mapped recipe with old data.
//        if (recipeDto.getId() != null) {
//            Recipe oldRecipe = recipeService.findRecipeByID(recipeDto.getId());
//            recipe.setRedditID(oldRecipe.getRedditID());
//            recipe.setSent(oldRecipe.isSent());
//        }
        return recipe;
    }
}
