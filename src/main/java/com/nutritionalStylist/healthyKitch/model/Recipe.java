package com.nutritionalStylist.healthyKitch.model;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.format.annotation.DateTimeFormat;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Entity
@Table(name = "Recipe")
public class Recipe extends NamedEntity{
    @Column(name = "created")
    //defines the precision of the date field
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date created;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "recipeImageID")
    private RecipeImage defaultImage;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeid")
    private Set<RecipeImage> recipeImages;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipeid")
    private List<MeasuredIngredient> measuredIngredients;

//    //NOTUS BETAS: mappedby annotation will not set the FK relationship whilst join column annotation will.
//    //@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recipe")
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "recipeid")
//    private Set<Instruction> instructions;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CuisineRecipe", joinColumns = @JoinColumn(name = "recipeID"),
            inverseJoinColumns = @JoinColumn(name = "cuisineID"))
    private Set<Cuisine> cuisines;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @Column(name="viewCount")
    private Integer viewCount;

    @Column(name="instructions")
    private String instructions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="createdByID")
    private User createdby;

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String displayPreviewImagePath() {
        return getDefaultImage().map(RecipeImage::displayPreviewImagePath).orElse("images/NoImage.jpg");
    }

    public String displayThumbnailImagePath() {
        return getDefaultImage().map(RecipeImage::displayThumbnailImagePath).orElse("images/NoImage.jpg");
    }

    public String displayOriginalImagePath() {
        return getDefaultImage().map(RecipeImage::displayOriginalImagePath).orElse("images/NoImage.jpg");
    }

    public User getCreatedby() {
        return createdby;
    }

    public void setCreatedby(User createdby) {
        this.createdby = createdby;
    }

    public Optional<RecipeImage> getDefaultImage() { return Optional.ofNullable(this.defaultImage); }

    public void setDefaultImage(RecipeImage recipeImage) { this.defaultImage=recipeImage; }


    public boolean hasDefaultImage() {
        return getDefaultImage().isPresent();
    }


    public List<MeasuredIngredient> getMeasuredIngredients(){ return measuredIngredients;}

    public void setMeasuredIngredients(List<MeasuredIngredient> measuredIngredients){ this.measuredIngredients = measuredIngredients;}

    public Set<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(Set<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public Set<NutritionalBenefit> getNutritionalBenefits() {
        return nutritionalBenefits;
    }

    public void setNutritionalBenefits(Set<NutritionalBenefit> nutritionalBenefits) {
        this.nutritionalBenefits = nutritionalBenefits;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        if(created != null)
            this.created = created;
    }

    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getInstructions() { return instructions; }

    public Set<RecipeImage> getRecipeImages() {
        return recipeImages;
    }

    public void setRecipeImages(Set<RecipeImage> recipeImages) {
        this.recipeImages = recipeImages;
    }

    //    protected List<MeasuredIngredient> getMesauredIngredientsInternal() {
//        if (this.measuredIngredients == null) {
//            this.measuredIngredients = new HashSet<>();
//        }
//        return this.measuredIngredients;
//    }

//    protected void setMeasuredIngredientsInternal(Set<MeasuredIngredient> measuredIngredients) {
//        this.measuredIngredients = measuredIngredients;
//    }

//    public List<MeasuredIngredient> sortedMeasuredIngredients() {
//        List<MeasuredIngredient> sortedMeasuredIngredients = new ArrayList<>(getMesauredIngredientsInternal());
//        PropertyComparator.sort(sortedMeasuredIngredients, new MutableSortDefinition("name", true, true));
//        return Collections.unmodifiableList(sortedMeasuredIngredients);
//    }

//    public void addMeasuredIngredient(MeasuredIngredient ingredient) {
//        getMesauredIngredientsInternal().add(ingredient);
//    }


//    //Instructions Area
//    protected List<Instruction> getInstructionsInternal() {
//        if (this.instructions == null) {
//            this.instructions = new ArrayList<>();
//        }
//        return this.instructions;
//    }

//    protected void setInstructionsInternal(List<Instruction> instructions) {
//        this.instructions = instructions;
//    }

//    public List<Instruction> sortedInstructions() {
//        List<Instruction> sortedInstructions = new ArrayList<>(getInstructionsInternal());
//        PropertyComparator.sort(sortedInstructions, new MutableSortDefinition("name", true, true));
//        return Collections.unmodifiableList(sortedInstructions);
//    }

//    public void addInstruction(Instruction instruction) {
//        getInstructionsInternal().add(instruction);
//    }



    //TODO: Add functionality to reassign ID's
    public void reassignSortIDs() {

    }

//    public Optional<Instruction> lastInstruction(){
//        return sortedInstructions().stream().reduce((first, second) -> second);
//    }


//    public  Optional<MeasuredIngredient> lastMesauredIngredient(){
//        return sortedMeasuredIngredients().stream().reduce((first, second) -> second);
//    }

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

    //Nutrional Benefits Area
    protected Set<Cuisine> getCuisinesInternal() {
        if (this.cuisines == null) {
            this.cuisines = new HashSet<>();
        }
        return this.cuisines;
    }

    protected void setCuisinesInternal(Set<Cuisine> cuisines) {
        this.nutritionalBenefits = nutritionalBenefits;
    }

    public List<Cuisine> sortedCuisines() {
        List<Cuisine> sortedCuisines = new ArrayList<>(getCuisinesInternal());
        PropertyComparator.sort(sortedCuisines, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedCuisines);
    }

    public void addCuisine(Cuisine cuisine) {
        getCuisinesInternal().add(cuisine);
    }

    public boolean cuisineExistsInRecipe(Cuisine value) {
        return getNutritionalBenefitInternal().contains(value);
    }

    //used by RecipeDTO
    public Integer getDefaultImageID(){
        return getDefaultImage().map(RecipeImage::getId).orElse(null);
    }



//    public RecipeImage createRecipeImage(HashMap<ImageQualityType, BufferedImage> imagesMap, String fileName) throws Exception{
//        RecipeImage recipeImage = new RecipeImage();
//        recipeImage.setRecipe(this);
//        recipeImage.setName(fileName);
//
//        String fileExtension = fileName.substring(fileName.lastIndexOf('.') +1, fileName.length());
//
//        for(ImageQualityType imageQualityType : imagesMap.keySet()){
//            RecipeFile newRecipeFile = new RecipeFile(imageQualityType);
//            BufferedImage image = imagesMap.get(imageQualityType);
//            ImageIO.write(image, fileExtension, new File(newRecipeFile.filePath()));
//
//        }
//
//        RecipeFile orginalFile = new RecipeFile(ImageQualityType.ORIGINAL);
//        RecipeFile previewFile = new RecipeFile(ImageQualityType.PREVIEW);
//        RecipeFile thumbnailFile = new RecipeFile(ImageQualityType.THUMBNAIL);
//        recipeImage.setOrginalImage(orginalFile);
//        recipeImage.setPreviewImage(previewFile);
//        recipeImage.setThumbnailImage(thumbnailFile);
//
//        return recipeImage;
//    }

    public RecipeImage createRecipeImage( String fileName) throws Exception{
        MimeMappings mimeMappings = new MimeMappings();
        RecipeImage recipeImage = new RecipeImage();
        //recipeImage.setRecipe(this);
        recipeImage.setName(fileName);

        RecipeFile orginalFile = new RecipeFile(fileName, ImageQualityType.ORIGINAL);
        RecipeFile previewFile = new RecipeFile(fileName, ImageQualityType.PREVIEW);
        RecipeFile thumbnailFile = new RecipeFile(fileName, ImageQualityType.THUMBNAIL);
        recipeImage.setOrginalImage(orginalFile);
        recipeImage.setPreviewImage(previewFile);
        recipeImage.setThumbnailImage(thumbnailFile);

        getRecipeImages().add(recipeImage);

        return recipeImage;
    }



    //public List<Instruction> getInstructions() {
    //    return new ArrayList<>(instructions);
    //}

//   // public void setInstructions(List<Instruction> instructions) {
//        this.instructions = new HashSet<>(instructions);
//    }

    public Set<DietaryCategory> getDietaryCategories() {
        return dietaryCategories;
    }

    public void setDietaryCategories(Set<DietaryCategory> dietaryCategories) {
        this.dietaryCategories = dietaryCategories;
    }

    public Set<Cuisine> getCuisines() { return cuisines; }

    public void setCuisines(Set<Cuisine> cuisines) { this.cuisines = cuisines; }

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

