package com.app.ClassBuddy.services;

import java.io.IOError;
import java.io.IOException;
import java.util.Optional;

import com.app.ClassBuddy.database.documents.Image;
import com.app.ClassBuddy.database.respositories.ImageRepository;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    
    @Autowired
    ImageRepository imageRepository;

    public String addImage(String title, MultipartFile file) throws IOException {
        Image image = new Image(title); 
        image.setImage(
          new Binary(BsonBinarySubType.BINARY, file.getBytes())); 
        image = imageRepository.insert(image); 
        return image.getId(); 
    }

    public Optional<Image> getImage(String id) {
        return imageRepository.findById(id);
    }
}
