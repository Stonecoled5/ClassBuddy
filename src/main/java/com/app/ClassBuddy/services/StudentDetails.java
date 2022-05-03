package com.app.ClassBuddy.services;

import java.util.Collection;

import com.app.ClassBuddy.database.documents.Student;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class StudentDetails implements UserDetails {
    Student student;

    public StudentDetails(Student student) {
        this.student = student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub

        return student.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    public String getFullName() {
        return student.getFirstName() + " " + student.getLastName();
    }

    // public Student getStudent() {
    //     return this.student;
    // }
    
}
