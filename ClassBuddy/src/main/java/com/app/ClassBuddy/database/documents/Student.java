package com.app.ClassBuddy.database.documents;

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

    public Student(String email, String password){
        this.email = email;
        this.password = password;
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