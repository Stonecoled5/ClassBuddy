package com.app.ClassBuddy.services;

import java.util.ArrayList;

import com.app.ClassBuddy.database.documents.Student;
import com.app.ClassBuddy.database.respositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student foundStudent = studentRepository.findByEmail(username); // username will be email

        if (foundStudent == null) return null;

        String email = foundStudent.getEmail();
        String password = foundStudent.getPassword();

        return new User(email, password, new ArrayList<>());
    }



    
    

}
