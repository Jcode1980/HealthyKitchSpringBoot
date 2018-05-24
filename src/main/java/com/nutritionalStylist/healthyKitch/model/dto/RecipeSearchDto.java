package com.nutritionalStylist.healthyKitch.model.dto;

import com.nutritionalStylist.healthyKitch.model.Instruction;
import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.MeasuredIngredient;

import java.util.ArrayList;
import java.util.Collection;

public class RecipeSearchDto {
    private Collection<String> searchStrings;
    private Collection<Integer> mealTypesID;
    private Collection<Integer> cuisinesID;
    private Collection<Integer> nutritionalBenefitID;
    private Collection<Integer> dietaryRequirementsID;
    private boolean searchForTrending;

    
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



    public Collection<String> getSearchStrings() { return searchStrings; }

    public void setSearchStrings(Collection<String> searchStrings) {
        this.searchStrings = searchStrings;
    }

    public boolean isSearchForTrending() { return searchForTrending; }

    public void setSearchForTrending(boolean searchForTrending) { this.searchForTrending = searchForTrending; }

    public boolean hasMealTypesSearch(){ return getMealTypesID() != null && getMealTypesID().size() > 0; }

    public boolean hasNutritionalBenefitSearch(){ return getNutritionalBenefitID() != null && getNutritionalBenefitID().size() > 0; }

    public boolean hasSearchStrings(){ return getSearchStrings() != null && getSearchStrings().size() > 0; }

    public boolean hasSomeKindOfSearch(){return hasMealTypesSearch() || hasNutritionalBenefitSearch() || hasSearchStrings();};

    public Collection<Integer> getCuisinesID() { return cuisinesID; }

    public void setCuisinesID(Collection<Integer> cuisinesID) { this.cuisinesID = cuisinesID; }

    public Collection<Integer> getDietaryRequirementsID() { return dietaryRequirementsID; }

    public void setDietaryRequirementsID(Collection<Integer> dietaryRequirementsID) { this.dietaryRequirementsID = dietaryRequirementsID; }
}
