package com.nutritionalStylist.healthyKitch.model.dto;

import com.nutritionalStylist.healthyKitch.model.Instruction;
import com.nutritionalStylist.healthyKitch.model.MealType;
import com.nutritionalStylist.healthyKitch.model.MeasuredIngredient;

import java.util.ArrayList;

public class RecipeDto {
   // private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Integer id;
    private String name;

    private ArrayList<Instruction> instructions;
    private ArrayList<MealType> mealTypes;
    private ArrayList<MeasuredIngredient> measuredIngredients;

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

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(ArrayList<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public ArrayList<MeasuredIngredient> getMeasuredIngredients() {
        return measuredIngredients;
    }

    public void setMeasuredIngredients(ArrayList<MeasuredIngredient> measuredIngredients) {
        this.measuredIngredients = measuredIngredients;
    }


}
