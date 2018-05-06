package com.nutritionalStylist.healthyKitch.model;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "File")
public abstract class File extends BaseEntity{
    private Date created;
    private int type;

    @PrePersist
    protected void onCreate() {
        created = new Date();
        type = type();
    }

    public abstract int type();


    public String filePath(){
            //return "/Users/john/Sites/Upload/" . $this->vars["fileID"];

            String filePath = System.getProperty("FILES_PRODUCTION_FOLDER", "/Users/john/Sites/Upload/") +
                getClass().getName() + "/" + getId();

            return filePath;
    }

    public String thumbnailImagePath(){
        String filePath = System.getProperty("FILE_PREVIEWS_PRODUCTION_FOLDER", "/Users/john/Sites/Upload/Preview") +
            getClass().getName() + "/" + getId();
        return filePath;

    }
}
