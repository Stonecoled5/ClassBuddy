package com.app.ClassBuddy.controllers;

import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import com.app.ClassBuddy.database.daos.StudentDAO;
import com.app.ClassBuddy.database.documents.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StudentController{


    @Autowired
    private StudentDAO studentDAO;

    @PostMapping("/students")
    public void addStudents(@RequestBody final List<Student> students){
        studentDAO.saveAll(students);
    }

    /*
     * Used for finding a student by a specific email (loggin in)
     */
    @GetMapping("/student/{email}")
    public Student getStudentByEmail(@PathVariable String email){
        return studentDAO.findByEmail(email);
    }


    @GetMapping("/students")
    public List<Student> findStudents(){
        return studentDAO.findAll();
    }

    @GetMapping("/students/{studentId}")
    public Student findStudent(@PathVariable final String studentId){
        return studentDAO.findById(studentId);
    }

    
}