package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.Views;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;
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
    @JsonIgnore
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

    @JsonView(Views.ListView.class)
    public List<RecipeDto> getRecipesDto(){
        List<RecipeDto> recipesDtos;
        if(getRecipes() != null){
            recipesDtos = (List<RecipeDto>)getRecipes().stream().map(RecipeDto::convertToDto).collect(Collectors.toList());
        }
        else{
            recipesDtos = Collections.emptyList();
        }
        return recipesDtos;
    }

    @JsonView(Views.ListView.class)
    public RecipeDto defaultRecipeDto() {
        if (getDefaultRecipe() != null) {
            return RecipeDto.convertToDto(getDefaultRecipe());
        }else{
            return null;
        }
    }
}
