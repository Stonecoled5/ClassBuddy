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

    final private String SCHOOL_DOMAIN = "wisc.edu";

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

    /*
     * This is for adding a single student to the database, during account creation
     */
    @PostMapping("/addStudent")
    public void addStudent(@RequestBody final Student student) throws InvalidAttributesException{
        // verify @wisc email
        if (!emailIsCorrect(student)) throw new InvalidAttributesException("You must use your @wisc.edu account... Account provided: " + student.getEmail());
        
        studentDAO.save(student);
    }

    @GetMapping("/students")
    public List<Student> findStudents(){
        return studentDAO.findAll();
    }

    @GetMapping("/students/{studentId}")
    public Student findStudent(@PathVariable final String studentId){
        return studentDAO.findById(studentId);
    }

    

    private boolean emailIsCorrect(Student student){
        String[] parts = student.getEmail().split("@");
        if (!parts[1].equals(SCHOOL_DOMAIN)){
            return false;
        } else {
            return true;
        }
    }
}