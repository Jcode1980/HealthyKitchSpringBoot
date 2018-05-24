package com.nutritionalStylist.healthyKitch.model;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Optional;
import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import org.springframework.boot.web.server.MimeMappings;

@Entity
public class RecipeImage extends NamedEntity {

    @ManyToOne
    @JoinColumn(name = "recipeID")
    private Recipe recipe;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "originalImageID")
    private RecipeFile orginalImage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thumbnailImageID")
    private RecipeFile thumbnailImage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "previewImageID")
    private RecipeFile previewImage;


    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Optional<RecipeFile> getOrginalImage() {
        return Optional.ofNullable(orginalImage);
    }

    public void setOrginalImage(RecipeFile orginalImage) {
        this.orginalImage = orginalImage;

    }

    public Optional<RecipeFile> getThumbnailImage() {
        return Optional.ofNullable(thumbnailImage);
    }

    public void setThumbnailImage(RecipeFile thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
        //thumbnailImage.setRecipeImage(this);
    }

    public Optional<RecipeFile> getPreviewImage() {
        return Optional.ofNullable(previewImage);
    }

    public void setPreviewImage(RecipeFile previewImage) {
        this.previewImage = previewImage;
        //previewImage.setRecipeImage(this);
    }

    public String displayPreviewImagePath() {
        return getPreviewImage().map(RecipeFile::filePath).orElse("images/NoImage.jpg");
    }

    public String displayThumbnailImagePath() {
        return getThumbnailImage().map(RecipeFile::filePath).orElse("images/NoImage.jpg");
    }

    public String displayOriginalImagePath() {
        return getOrginalImage().map(RecipeFile::filePath).orElse("images/NoImage.jpg");
    }

    public Optional<RecipeFile> recipeFileForImageType(ImageQualityType imageQualityType){
        System.out.println("I'm in here");
        if(ImageQualityType.PREVIEW.equals(imageQualityType)){
            return getPreviewImage();
        }
        else if(ImageQualityType.THUMBNAIL.equals(imageQualityType)){
                return getThumbnailImage();
        }
        else{
            return getOrginalImage();
        }
    }



//    public String displayThumbnailImagePath() {
//        return getDefaultImage().map(RecipeImage::filePath).orElse("images/NoImage.jpg");
//    }
//
//    public String displayOriginalImagePath() {
//        return getDefaultImage().map(RecipeImage::filePath).orElse("images/NoImage.jpg");
//    }
}

