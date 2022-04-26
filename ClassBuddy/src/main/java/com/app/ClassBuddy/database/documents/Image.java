package com.app.ClassBuddy.database.documents;

import javax.websocket.Decoder.Binary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document(collation = "profile-pictures")
public class Image {

    public Image(String title2) {
        this.title = title2;
    }
    public Image(MultipartFile file) {
        this.file = file;
    }
    @Id
    private String id;
    private String title;
    private Binary image;
    private MultipartFile file;

    public Image() {

    }

    public void setImage(org.bson.types.Binary binary) {
        this.image = (Binary) binary;
    }
    public String getId() {
        return this.id;
    }


    
}
