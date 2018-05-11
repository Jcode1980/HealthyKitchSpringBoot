package com.nutritionalStylist.healthyKitch.model.dto;

import com.nutritionalStylist.healthyKitch.model.Instruction;
import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.MeasuredIngredient;

import java.util.ArrayList;
import java.util.Collection;

public class RecipeSearchDto {
    private String searchString;
    private Collection<Integer> mealTypesID;
    private Collection<Integer> nutritionalBenefitID;


    public Collection<Integer> getMealTypesID() {
        return mealTypesID;
    }

    public void setMealTypesID(Collection<Integer> mealTypes) {
        this.mealTypesID = mealTypes;
    }

    public Collection<Integer> getNutritionalBenefitID() {
        return nutritionalBenefitID;
    }

    public void setNutritionalBenefitID(Collection<Integer> nutritionalBenefitID) { this.nutritionalBenefitID = nutritionalBenefitID; }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }


}
