package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="cookbook")
public class Cookbook extends NamedEntity {
    @Column(name = "created")
    //defines the precision of the date field
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date created;

    @ManyToOne
    private Recipe defaultRecipe;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CookbookRecipe", joinColumns = @JoinColumn(name = "cookbookId"),
            inverseJoinColumns = @JoinColumn(name = "recipeID"))
    private Set<Recipe> recipes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="createdByID")
    private User createdby;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Cookbook(User user){
        this.createdby = user;
    }

    public Cookbook(){}


    public Recipe getDefaultRecipe() { return defaultRecipe; }

    public void setDefaultRecipe(Recipe defaultRecipe) { this.defaultRecipe = defaultRecipe; }

    @JsonIgnore
    public Set<Recipe> getRecipes() { return recipes; }

    public void setRecipes(Set<Recipe> recipes) { this.recipes = recipes; }

    public Set<RecipeDto> getRecipesDto(){
        return (Set<RecipeDto>)getRecipes().stream().map(RecipeDto::convertToDto).collect(Collectors.toList());
    }

}
