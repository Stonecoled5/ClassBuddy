package com.app.ClassBuddy.database.daos;

import com.app.ClassBuddy.database.documents.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CourseDAO {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public void save(Course course) {
        mongoTemplate.save(course);
    }

    // public Course getCourseByName(String dept, String num) {
        
    // }

}
