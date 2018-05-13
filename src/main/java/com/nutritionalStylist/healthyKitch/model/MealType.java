package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.*;
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
    private File image;
}
