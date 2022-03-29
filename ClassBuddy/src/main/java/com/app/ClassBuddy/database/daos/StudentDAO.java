package com.app.ClassBuddy.database.daos;

import java.util.List;

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

    public Student findById(final String productId){
        return mongoTemplate.findById(productId, Student.class);
    }

    public Student findByEmail(final String email){
        return mongoTemplate.findOne(BasicQuery.query(Criteria.where("email").is(email)), Student.class);
    }

    public void save(Student student){
        mongoTemplate.save(student);
    }

}