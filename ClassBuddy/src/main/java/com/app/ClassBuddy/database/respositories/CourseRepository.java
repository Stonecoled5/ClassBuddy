package com.app.ClassBuddy.database.respositories;

import com.app.ClassBuddy.database.documents.Course;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    

    
}
