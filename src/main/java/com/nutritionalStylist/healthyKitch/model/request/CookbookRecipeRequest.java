package com.nutritionalStylist.healthyKitch.model.request;

public class CookbookRecipeRequest {
    private int cookbookID;
    private int recipeID;


    public int getCookbookID() {
        return cookbookID;
    }

    public void setCookbookID(int cookbookID) {
        this.cookbookID = cookbookID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipdeID) {
        this.recipeID = recipdeID;
    }
}
