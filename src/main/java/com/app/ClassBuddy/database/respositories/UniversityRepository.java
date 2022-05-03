package com.app.ClassBuddy.database.respositories;

import com.app.ClassBuddy.database.documents.University;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends MongoRepository<University, String> {
    
    University findByName(String name);
}