package com.nutritionalStylist.healthyKitch.model;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "File")
public class RecipeFile extends File {
    @Column(name = "imageType")
    protected  int qualityType;

    @Column(name="imageQualityType")
    @NotNull
    protected String imageQualityType;

    @Column(name = "height")
    protected  int height;

    @Column(name = "width")
    protected  int width;

    @Override
    public int type() {
        return 1;
    }

    public String filePath(){
        String filePath = System.getProperty("FILES_PRODUCTION_FOLDER", "/Users/johnadolfo/Desktop/WorkRelated/HK/Production/") +
                getClass().getName() + "/" + imageQualityType + "/" + getId();

        return filePath;
    }

    public String imageQualityType(){return imageQualityType;}

    public void setImageQualityTypeEnum(ImageQualityType qualityTypeEnum){imageQualityType=qualityTypeEnum.name().toLowerCase();}


}
