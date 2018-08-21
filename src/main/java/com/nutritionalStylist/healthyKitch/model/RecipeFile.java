package com.nutritionalStylist.healthyKitch.model;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "File")
public class RecipeFile extends File {

    @Column(name="imageQualityType")
    @NotNull
    protected String imageQualityType;



//    @ManyToOne
//    @JoinColumn(name = "recipeImageID")
//    private RecipeImage recipeImage;




    @Override
    public int type() {
        return 1;
    }

    public RecipeFile(String fileName, ImageQualityType qualityType){
        setFileName(fileName);
        this.imageQualityType = qualityType.name();
    }

    public RecipeFile(){ }

//    public RecipeImage getRecipeImage() { return recipeImage; }
//
//    public void setRecipeImage(RecipeImage recipeImage) { this.recipeImage = recipeImage; }


    public  String fileFolder(){ return "RecipeImage/"; }

    public String filePath(){
        return productionFolder() + fileFolder() + imageQualityType.toLowerCase() + "/" + getId() + "." + fileExtension() ;

    }

    public String imageQualityType(){return imageQualityType;}

    public void setImageQualityTypeEnum(ImageQualityType qualityTypeEnum){imageQualityType=qualityTypeEnum.name().toLowerCase();}


}
