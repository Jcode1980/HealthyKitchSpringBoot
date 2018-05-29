package com.nutritionalStylist.healthyKitch.model.dto;

import com.nutritionalStylist.healthyKitch.model.DietaryCategory;
import com.nutritionalStylist.healthyKitch.model.Instruction;
import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.MeasuredIngredient;

import java.util.ArrayList;
import java.util.Collection;

public class RecipeDto {
   // private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Integer id;
    private String name;
    private Integer defaultImageID;
    private Collection<Instruction> instructions;
    private Collection<MealType> mealTypes;
    private Collection<MeasuredIngredient> measuredIngredients;
    private Collection<DietaryCategory> dietaryCategories;

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

    public Collection<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(Collection<Instruction> instructions) {
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

    public Integer getDefaultImageID() {
        return defaultImageID;
    }

    public void setDefaultImageID(Integer defaultImageID) {
        this.defaultImageID = defaultImageID;
    }

}
