package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecipeImage extends File {

    @ManyToOne
    @JoinColumn(name = "recipeID")
    private Recipe recipe;

    @Override
    public int type() {
        return 1;
    }
}

