package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;


@Entity
@Table(name = "Recipe")
public class Recipe extends NamedEntity{
    @Column(name = "created")
    //defines the precision of the date field
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date created;

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date deleted;

    private Integer numServings;

    private Integer readyInMins;



    private String descText;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "recipeImageID")
    private RecipeImage defaultImage;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeid")
    private Set<RecipeImage> recipeImages;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipeid")
    private Set<IngredientSubHeading> ingredientSubHeadings;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipeid")
    private Set<MeasuredIngredient> measuredIngredients;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="recipeStatusID")
    private RecipeStatus recipeStatus;


    private BigDecimal averageRating;

    private Integer numberOfReviews;

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

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public Set<MeasuredIngredient> getMeasuredIngredients(){ return measuredIngredients;}

    public void setMeasuredIngredients(Set<MeasuredIngredient> measuredIngredients){ this.measuredIngredients = measuredIngredients;}

    public Set<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(Set<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public Integer getNumServings() {
        return numServings;
    }

    public void setNumServings(Integer numServings) {
        this.numServings = numServings;
    }

    public Integer getReadyInMins() {
        return readyInMins;
    }

    public void setReadyInMins(Integer readyInMins) {
        this.readyInMins = readyInMins;
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

    public RecipeStatus getRecipeStatus() { return recipeStatus; }

    public void setRecipeStatus(RecipeStatus recipeStatus) { this.recipeStatus = recipeStatus; }

    public BigDecimal getAverageRating() { return averageRating; }

    public void setAverageRating(BigDecimal averageRating) { this.averageRating = averageRating; }

    public Integer getNumberOfReviews() { return numberOfReviews; }

    public void setNumberOfReviews(Integer numberOfReviews) { this.numberOfReviews = numberOfReviews; }

    //TODO: Add functionality to reassign ID's
    public void reassignSortIDs() {

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
    private Set<NutritionalBenefit> getNutritionalBenefitInternal() {
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
    private Set<Cuisine> getCuisinesInternal() {
        if (this.cuisines == null) {
            this.cuisines = new HashSet<>();
        }
        return this.cuisines;
    }

    private void setCuisinesInternal(Set<Cuisine> cuisines) {
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
        return getCuisinesInternal().contains(value);
    }

    //used by RecipeDTO
    public Integer getDefaultImageID(){
        return getDefaultImage().map(RecipeImage::getId).orElse(null);
    }

    public Integer getCreateByUserID(){
        return getCreatedby().getId();
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
        RecipeImage recipeImage = new RecipeImage();
        recipeImage.setName(fileName);
        recipeImage.setRecipe(this);

        RecipeFile orginalFile = new RecipeFile(fileName, ImageQualityType.ORIGINAL);
        RecipeFile previewFile = new RecipeFile(fileName, ImageQualityType.PREVIEW);
        RecipeFile thumbnailFile = new RecipeFile(fileName, ImageQualityType.THUMBNAIL);
        recipeImage.setOrginalImage(orginalFile);
        recipeImage.setPreviewImage(previewFile);
        recipeImage.setThumbnailImage(thumbnailFile);

        getRecipeImages().add(recipeImage);

        return recipeImage;
    }

    public Set<IngredientSubHeading> getIngredientSubHeadings() {
        return ingredientSubHeadings;
    }

    public void setIngredientSubHeadings(Set<IngredientSubHeading> ingredientSubHeadings) {
        this.ingredientSubHeadings = ingredientSubHeadings;
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

    public Date getDeleted() { return deleted; }

    public void setDeleted(Date deleted) { this.deleted = deleted; }


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

