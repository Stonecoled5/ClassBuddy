package com.app.ClassBuddy.database.daos;

import java.util.ArrayList;
import java.util.List;

import com.app.ClassBuddy.database.documents.Course;
import com.app.ClassBuddy.database.documents.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Student> findAll(){
        return mongoTemplate.findAll(Student.class);
    }

    public void saveAll(List<Student> students){
        mongoTemplate.insertAll(students);
    }

    public Student findById(final String studentId){
        return mongoTemplate.findById(studentId, Student.class);
    }

    public Student findByEmail(final String email){
        return mongoTemplate.findOne(BasicQuery.query(Criteria.where("email").is(email)), Student.class);
    }

    public void addCourseToSchedule(String email, Course c) {

        Student s = findByEmail(email);
        if (s != null) {

            // check if course is in schedule
            if (s.getSchedule().contains(c)) {
                return;
            }
            s.setSchedule(c);
            mongoTemplate.save(s);
        }
    }
    
    public void algorithm() {
       List<Student> students = findAll();

       for (Student s : students){
            // iterating through entire document... find schedules
            for (Course c : s.getSchedule()) {
                if ()
            }
       }
       
    }

    public void setFirstName(String email, String name) {
        Student s = findByEmail(email);
        if (s != null){
            s.setFirstName(name);
            mongoTemplate.save(s);
            System.out.println(s.getFirstName());
        }
    }

    public void setLastName(String email, String name) {
        Student s = findByEmail(email);
        if (s != null){
            s.setLastName(name);
            mongoTemplate.save(s);
        }
    }

    
    public void save(Student student){
        mongoTemplate.save(student);
    }

}