package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="File")
public class MealTypeFile extends File{

    public MealTypeFile(String name){
        setFileName(name);
    }

    public MealTypeFile(){};

    @Override
    public int type() {
        return 2;
    }

    @Override
    public String fileFolder() { return "MealTypeFile/"; }

    @Override
    public String filePath(){
        return productionFolder() + fileFolder() + getId() + "." + fileExtension() ;

    }
}
