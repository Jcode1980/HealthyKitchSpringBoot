package com.nutritionalStylist.healthyKitch.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class RecipeDto {
   // private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static private ModelMapper MODEL_MAPPER;
    @JsonView({Views.ListView.class})
    private Integer id;
    @JsonView({Views.ListView.class})
    private String name;
    @JsonView({Views.ListView.class})
    private Integer defaultImageID;
    @JsonView({Views.ListView.class})
    private Integer numServings;
    @JsonView({Views.ListView.class})
    private BigDecimal readyInMins;
    @JsonView({Views.DetailedView.class})
    private String descText;
    @JsonView({Views.DetailedView.class})
    private String instructions;
    @JsonView({Views.DetailedView.class})
    private Set<MealType> mealTypes;
    @JsonView({Views.DetailedView.class})
    private List<MeasuredIngredient> measuredIngredients;
    @JsonView({Views.DetailedView.class})
    private Set<DietaryCategory> dietaryCategories;
    @JsonView({Views.DetailedView.class})
    private Set<Cuisine> cuisines;
    @JsonView({Views.DetailedView.class})
    private Set<NutritionalBenefit> nutritionalBenefits;


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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDescText() { return descText; }

    public void setDescText(String descText) { this.descText = descText; }

    public Collection<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(Set<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public List<MeasuredIngredient> getMeasuredIngredients() {
        return measuredIngredients;
    }

    public void setMeasuredIngredients(List<MeasuredIngredient> measuredIngredients) {
        this.measuredIngredients = measuredIngredients;
    }

    public Set<DietaryCategory> getDietaryCategories() {
        return dietaryCategories;
    }

    public void setDietaryCategories(Set<DietaryCategory> dietaryCategories) {
        this.dietaryCategories = dietaryCategories;
    }

    public Set<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(Set<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public Set<NutritionalBenefit> getNutritionalBenefits() {
        return nutritionalBenefits;
    }

    public void setNutritionalBenefits(Set<NutritionalBenefit> nutritionalBenefits) {
        this.nutritionalBenefits = nutritionalBenefits;
    }

    public Integer getDefaultImageID() {
        return defaultImageID;
    }

    public void setDefaultImageID(Integer defaultImageID) {
        this.defaultImageID = defaultImageID;
    }

    public Integer getNumServings() {
        return numServings;
    }

    public void setNumServings(Integer numServings) {
        this.numServings = numServings;
    }

    public BigDecimal getReadyInMins() {
        return readyInMins;
    }

    public void setReadyInMins(BigDecimal readyInMins) {
        this.readyInMins = readyInMins;
    }

    static public RecipeDto convertToDto(Recipe recipe) {
        RecipeDto recipeDto =  MODEL_MAPPER.map(recipe, RecipeDto.class);
        List<MeasuredIngredient> sortedIngredients = recipeDto.measuredIngredients.stream().sorted(Comparator.comparing(MeasuredIngredient::getSortID)).collect(Collectors.toList());
        System.out.println("sorted ingredient: " + sortedIngredients);
        recipeDto.setMeasuredIngredients(sortedIngredients);
        return recipeDto;
    }

    static public Recipe convertToEntity(RecipeDto recipeDto) {
        return MODEL_MAPPER.map(recipeDto, Recipe.class);

        //Enrich mapped recipe with old data.
//        if (recipeDto.getId() != null) {
//            Recipe oldRecipe = recipeService.findRecipeByID(recipeDto.getId());
//            recipe.setRedditID(oldRecipe.getRedditID());
//            recipe.setSent(oldRecipe.isSent());
//        }
//        return recipe;
    }
            }
