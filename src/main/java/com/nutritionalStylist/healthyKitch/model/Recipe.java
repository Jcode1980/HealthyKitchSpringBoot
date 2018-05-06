package com.nutritionalStylist.healthyKitch.model;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Recipe")
public class Recipe extends NamedEntity{
    @Column(name = "created")
    //defines the precision of the date field
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date created;

    @ManyToOne
    @JoinColumn(name = "recipeImageID")
    private RecipeImage defaultImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<MeasuredIngredient> measuredIngredients;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Instruction> instructions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "NutritionalBenefitRecipe", joinColumns = @JoinColumn(name = "recipeID"),
        inverseJoinColumns = @JoinColumn(name = "nutritionalBenefitID"))
    private Set<NutritionalBenefit> nutritionalBenefits;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MealTypeRecipe", joinColumns = @JoinColumn(name = "recipeID"),
        inverseJoinColumns = @JoinColumn(name = "mealTypeID"))
    private Set<MealType> mealTypes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DietaryCategoryRecipe", joinColumns = @JoinColumn(name = "recipeID"),
        inverseJoinColumns = @JoinColumn(name = "dietaryCategoryID"))
    private Set<DietaryCategory> dietaryCategories;




    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public String displayImagePath() {
        return getDefaultImage().map(RecipeImage::filePath).orElse("images/NoImage.jpg");
    }

    public String displayImageThumbnailPath() {
        return getDefaultImage().map(RecipeImage::thumbnailImagePath).orElse("images/NoImage.jpg");
    }

    public Optional<RecipeImage> getDefaultImage() { return Optional.ofNullable(this.defaultImage); }

    public boolean hasDefaultImage() {
        return getDefaultImage().isPresent();
    }


    protected Set<MeasuredIngredient> getMesauredIngredientsInternal() {
        if (this.measuredIngredients == null) {
            this.measuredIngredients = new HashSet<>();
        }
        return this.measuredIngredients;
    }

    protected void setMeasuredIngredientsInternal(Set<MeasuredIngredient> measuredIngredients) {
        this.measuredIngredients = measuredIngredients;
    }

    public List<MeasuredIngredient> sortedMeasuredIngredients() {
        List<MeasuredIngredient> sortedMeasuredIngredients = new ArrayList<>(getMesauredIngredientsInternal());
        PropertyComparator.sort(sortedMeasuredIngredients, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedMeasuredIngredients);
    }

    public void addMeasuredIngredient(MeasuredIngredient ingredient) {
        getMesauredIngredientsInternal().add(ingredient);
    }


    //Instructions Area
    protected List<Instruction> getInstructionsInternal() {
        if (this.instructions == null) {
            this.instructions = new ArrayList<>();
        }
        return this.instructions;
    }

    protected void setInstructionsInternal(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public List<Instruction> sortedInstructions() {
        List<Instruction> sortedInstructions = new ArrayList<>(getInstructionsInternal());
        PropertyComparator.sort(sortedInstructions, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedInstructions);
    }

    public void addInstruction(Instruction instruction) {
        getInstructionsInternal().add(instruction);
    }



    //TODO: Add functionality to reassign ID's
    public void reassignSortIDs() {

    }


    public Optional<Instruction> lastInstruction(){
        return sortedInstructions().stream().reduce((first, second) -> second);
    }


    public  Optional<MeasuredIngredient> lastMesauredIngredient(){
        return sortedMeasuredIngredients().stream().reduce((first, second) -> second);
    }

    public boolean mealTypeExistsInRecipe(MealType mealType){
        return getMealTypesInternal().contains(mealType);
    }


    protected Set<MealType> getMealTypesInternal() {
        if (this.mealTypes == null) {
            this.mealTypes = new HashSet<>();
        }
        return this.mealTypes;
    }


    //Instructions Area
    protected Set<DietaryCategory> getDietaryCategoriesInternal() {
        if (this.dietaryCategories == null) {
            this.dietaryCategories = new HashSet<>();
        }
        return this.dietaryCategories;
    }

    public List<DietaryCategory> sortedDietaryCategories(){
        List<DietaryCategory> sortedDietaryCategory = new ArrayList<>(getDietaryCategoriesInternal());
        PropertyComparator.sort(sortedDietaryCategory, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedDietaryCategory);
    }

    public boolean dietaryCategoryExistsInRecipe(DietaryCategory dietaryCategory){
        return getDietaryCategoriesInternal().contains(dietaryCategory);
    }

    public void addDietaryCategory(DietaryCategory dietaryCategory){
        getDietaryCategoriesInternal().add(dietaryCategory);
    }


    //Nutrional Benefits Area

    protected Set<NutritionalBenefit> getNutritionalBenefitInternal() {
        if (this.nutritionalBenefits == null) {
            this.nutritionalBenefits = new HashSet<>();
        }
        return this.nutritionalBenefits;
    }

    protected void setNutritionalBenefitsInternal(Set<NutritionalBenefit> nutritionalBenefits) {
        this.nutritionalBenefits = nutritionalBenefits;
    }

    public List<NutritionalBenefit> sortedNutritionalBenefits() {
        List<NutritionalBenefit> sortedNutrionalBenefits = new ArrayList<>(getNutritionalBenefitInternal());
        PropertyComparator.sort(sortedNutrionalBenefits, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedNutrionalBenefits);
    }

    public void addNutritionalBenefit(NutritionalBenefit nutritionalBenefit) {
        getNutritionalBenefitInternal().add(nutritionalBenefit);
    }

    public boolean nutritionalBenefitExistsInRecipe(NutritionalBenefit value) {
        return getNutritionalBenefitInternal().contains(value);
    }


//    public function removeMealType($mealType){
//        $this->removeObjectFromManyToManytRelationship($mealType, "MealTypeRecipe", "mealTypeID");
//    }
//
//    public function removeDietaryCategory($dietaryCategory){
//        $this->removeObjectFromManyToManytRelationship($dietaryCategory, "DietaryCategoryRecipe", "dietaryCategoryID");
//    }
//
//    public function removeNutritionalBenefit($nutritionalBenefit){
//        $this->removeObjectFromManyToManytRelationship($nutritionalBenefit, "NutritionalBenefitRecipe", "nutritionalBenefitID");
//    }



//    public function userCanEditRecipe($user){
//        if($this->field("userID") != null && $user != null){
//            $createdByUser = $this->valueForRelationship("user");
//            if($createdByUser != null){
//                return ($user->field("id") == $createdByUser->field("userID"));
//            }
//            else{
//                return true;
//            }
//        }
//        else
//            return true;
//
//    }


//    public function readyInDisplay(){
//        $readyInString = "";
//        if($this->field("readyIn")){
//            $readyInString = $this->field("readyIn");
//        }
//
//        if($this->field("readyInMetric")){
//            $readyInString = $readyInString . (($this->field("readyInMetric") == 1) ? "Hrs" : "Mins");
//        }
//
//        return $readyInString;
//    }


}

