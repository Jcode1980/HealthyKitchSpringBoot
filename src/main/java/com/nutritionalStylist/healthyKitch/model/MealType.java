package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.util.Set;

@Entity
@Table(name="MealType")
public class MealType extends NamedEntity {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MealTypeRecipe", joinColumns = @JoinColumn(name = "mealTypeID"),
        inverseJoinColumns = @JoinColumn(name = "recipeID"))
    private Set<Recipe> recipes;

    @ManyToOne
    @JoinColumn(name = "imageID")
    private MealTypeFile image;

    public MealTypeFile getImage() {
        return image;
    }

    public void setImage(MealTypeFile image) {
        this.image = image;
    }


}
