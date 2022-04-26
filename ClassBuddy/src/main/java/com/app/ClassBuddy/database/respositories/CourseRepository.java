package com.app.ClassBuddy.database.respositories;

import java.util.List;

import com.app.ClassBuddy.database.documents.Course;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    
    Course findByFullCourseName(String name);

    List<Course> findCourseByAbbreviation(String abbreviation);
    
}
