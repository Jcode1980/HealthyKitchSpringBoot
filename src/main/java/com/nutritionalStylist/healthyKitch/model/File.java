package com.nutritionalStylist.healthyKitch.model;

import com.nutritionalStylist.healthyKitch.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "File")
public abstract class File extends BaseEntity{
    public static final MimeMappings mimeMappings = new MimeMappings();
    @Column(name = "created")
    private Date created;

    @Column(name = "type")
    private int type;

    @Column(name = "mimeType")
    private String mimeType;

    @Column(name = "fileSize")
    private int fileSize;

    @PrePersist
    protected void onCreate() {
        created = new Date();
        type = type();
    }

    protected String fileName;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public abstract int type();

    public abstract String filePath();

    public abstract String fileFolder();

    //Maybe in future check if fileName doesn't exists in the directory?
    //however this might not be an issue isn't tmp fils will be moved to orginal file location
    public static String getUniqueFileName(String directory, String extension) {
        String fileName = MessageFormat.format("{0}.{1}", UUID.randomUUID(), extension.trim());
        return Paths.get(directory, fileName).toString();
    }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        setMimeType(mimeMappings.get(fileExtension()));

    }

    public String fileExtension(){
        return getFileName().substring(getFileName().lastIndexOf('.') +1, getFileName().length());
    }
}
