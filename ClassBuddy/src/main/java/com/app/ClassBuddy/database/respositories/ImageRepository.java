package com.app.ClassBuddy.database.respositories;

import com.app.ClassBuddy.database.documents.Image;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
 
    
}
