package com.app.ClassBuddy.database.respositories;

import com.app.ClassBuddy.database.documents.Student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    
    Student findByEmail(String email);
}
