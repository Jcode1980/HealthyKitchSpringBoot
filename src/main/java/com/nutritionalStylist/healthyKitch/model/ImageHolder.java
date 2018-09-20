package com.nutritionalStylist.healthyKitch.model;

import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "ImageHolder")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class ImageHolder extends  NamedEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "originalImageID")
    private File orginalImage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thumbnailImageID")
    private File thumbnailImage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "previewImageID")
    private File previewImage;


    public Optional<File> getOrginalImage() {
        return Optional.ofNullable(orginalImage);
    }

    public void setOrginalImage(File orginalImage) {
        this.orginalImage = orginalImage;
    }

    public Optional<File> getThumbnailImage() {
        return Optional.ofNullable(thumbnailImage);
    }

    public void setThumbnailImage(File thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
        //thumbnailImage.setRecipeImage(this);
    }

    public Optional<File> getPreviewImage() {
        return Optional.ofNullable(previewImage);
    }

    public void setPreviewImage(File previewImage) {
        this.previewImage = previewImage;
        //previewImage.setRecipeImage(this);
    }

    public String displayPreviewImagePath() {
        return getPreviewImage().map(File::filePath).orElse("images/NoImage.jpg");
    }

    public String displayThumbnailImagePath() {
        return getThumbnailImage().map(File::filePath).orElse("images/NoImage.jpg");
    }

    public String displayOriginalImagePath() {
        return getOrginalImage().map(File::filePath).orElse("images/NoImage.jpg");
    }

    public Optional<File> fileForImageType(ImageQualityType imageQualityType){
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

}
