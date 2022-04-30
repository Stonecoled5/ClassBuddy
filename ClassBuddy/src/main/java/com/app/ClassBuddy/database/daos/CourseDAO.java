package com.app.ClassBuddy.database.daos;

import java.util.List;

import com.app.ClassBuddy.database.documents.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDAO {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public void save(Course course) {
        mongoTemplate.save(course);
    }

    public List<Course> findCourseByName(String abbreviation) {
       return mongoTemplate.findAll(Course.class, abbreviation);
    }

    // public Course getCourseByName(String dept, String num) {
        
    // }

}
