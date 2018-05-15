package com.nutritionalStylist.healthyKitch.enums;

public enum ImageType {
    THUMBNAIL(200,200), PREVIEW(600,600);

    private int width;
    private int height;

    ImageType(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
