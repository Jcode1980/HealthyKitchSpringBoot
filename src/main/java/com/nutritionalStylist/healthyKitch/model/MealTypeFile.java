package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "2")
public class MealTypeFile extends File{

    public MealTypeFile(String name){
        setFileName(name);
    }

    public MealTypeFile(){};

    @Override
    public String fileFolder() { return "MealTypeFile/"; }

    @Override
    public String filePath(){
        return productionFolder() + fileFolder() + getId() + "." + fileExtension() ;

    }
}
