package com.nutritionalStylist.healthyKitch.enums;

public enum ImageQualityType {
    THUMBNAIL(200,200), PREVIEW(600,600), ORIGINAL(10000,10000);
    private int width;
    private int height;

    ImageQualityType( int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth(){ return width; }

    public int getHeight(){
        return height;
    }
}
