
package com.app.ClassBuddy.database.respositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import com.app.ClassBuddy.database.documents.Student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    

    Stream<Student> findAllBy();

    Student findByEmail(String email);
}
