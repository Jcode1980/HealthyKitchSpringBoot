package com.nutritionalStylist.healthyKitch.enums;

import java.util.ArrayList;
import java.util.List;

public enum ImageQualityType {
    THUMBNAIL(320,320, 3), PREVIEW(600,600, 2), ORIGINAL(10000,10000, 1);
    private int width;
    private int height;
    private int id;

    public static List<ImageQualityType> imageQualityTypes = new ArrayList<>();

    static{
        imageQualityTypes.add(THUMBNAIL);
        imageQualityTypes.add(PREVIEW);
        imageQualityTypes.add(ORIGINAL);
    }

    ImageQualityType( int width, int height, int id) {
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public int getWidth(){ return width; }

    public int getHeight(){
        return height;
    }

    public int getID() {return id;};


    public static ImageQualityType imageTypeForID(int value) throws IllegalArgumentException{
        return imageQualityTypes.stream().filter(currentImageType -> currentImageType.getID() == value).findFirst().orElseThrow(() ->
                new IllegalArgumentException("ImageQuality Type with value: " + value + " not found"));
    }



}
