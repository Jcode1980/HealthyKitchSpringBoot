package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.model.dto.RecipeDto;
import com.nutritionalStylist.healthyKitch.model.dto.Views;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
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

    //Nutrional Benefits Area
    private Set<Recipe> getRecipesInternal() {
        if (this.recipes == null) {
            this.recipes = new HashSet<>();
        }
        return this.recipes;
    }

    private void setRecipesInternal(Set<Cuisine> cuisines) {
        this.recipes = recipes;
    }

    public List<Recipe> sortedRecipes() {
        List<Recipe> sortedRecipes = new ArrayList<>(getRecipesInternal());
        PropertyComparator.sort(sortedRecipes, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedRecipes);
    }

    public boolean addRecipe(Recipe recipe) {
        if(!recipeExistsInCookbook(recipe)){
            getRecipesInternal().add(recipe);
            return true;
        }
        else{
            return false;
        }

    }

    public boolean recipeExistsInCookbook(Recipe value) {
        return getRecipesInternal().contains(value);
    }


}
