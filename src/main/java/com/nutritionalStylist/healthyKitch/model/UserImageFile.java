package com.nutritionalStylist.healthyKitch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue(value = "3")
public class UserImageFile extends File{
    @Column(name="imageQualityType")
    @NotNull
    protected String imageQualityType;



    public UserImageFile(String fileName, ImageQualityType qualityType){
        setFileName(fileName);
        this.imageQualityType = qualityType.name();
    }


    @JsonIgnore
    public  String fileFolder(){ return "UserImageFile/"; }

    @JsonIgnore
    public String filePath(){
        return productionFolder() + fileFolder() + imageQualityType.toLowerCase() + "/" + getId() + "." + fileExtension() ;

    }
}
