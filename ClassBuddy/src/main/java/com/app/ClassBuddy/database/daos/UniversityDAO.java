package com.app.ClassBuddy.database.daos;

import java.util.ArrayList;

import com.app.ClassBuddy.database.documents.University;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityDAO {
 
    @Autowired
    MongoTemplate mongoTemplate;

    public void save(University university) {
        mongoTemplate.save(university);
    }


    public University getUniversityMadison() {
        System.out.println("IN here");
        return mongoTemplate.findOne(BasicQuery.query(Criteria.where("name").is("UW-Madison")), University.class);
    }

    

}
