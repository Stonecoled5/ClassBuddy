package com.app.ClassBuddy.database.documents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Student {

    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private int year; // 1 = Fresh. 2 = Soph. 3 = Jr. 4 = Sr. 5 = Other
    private String username;
    private ArrayList<Course> schedule;

    public Student(String email, String password){
        this.email = email;
        this.password = password;
        this.schedule = new ArrayList<>();
    }


    public Student() {
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Course> getSchedule() {
        return this.schedule;
    }

    public void setSchedule(ArrayList<Course> schedule) {
        this.schedule = schedule;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchedule(List<Course> courses) {
        for (Course c : courses){
            setSchedule(c);
        }
    }

    public void setSchedule(Course course) {
        schedule.add(course);
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getYear() {
        return this.year;
    }
    private String password;

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }


}