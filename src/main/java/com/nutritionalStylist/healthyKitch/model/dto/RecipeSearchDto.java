package com.nutritionalStylist.healthyKitch.model.dto;

import com.nutritionalStylist.healthyKitch.model.Instruction;
import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.MeasuredIngredient;

import java.util.ArrayList;
import java.util.Collection;

public class RecipeSearchDto {
    private String searchString;
    private Collection<Integer> mealTypesID;

    public Collection<Integer> getMealTypesID() {
        return mealTypesID;
    }

    public void setMealTypesID(Collection<Integer> mealTypes) {
        this.mealTypesID = mealTypes;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
