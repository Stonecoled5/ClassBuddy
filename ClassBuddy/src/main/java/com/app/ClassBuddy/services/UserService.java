package com.app.ClassBuddy.services;

import java.util.ArrayList;

import com.app.ClassBuddy.database.documents.Student;
import com.app.ClassBuddy.database.dtos.StudentRegistrationDto;
import com.app.ClassBuddy.database.respositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    public Student save(StudentRegistrationDto registrationDto) {
        System.out.println("creating sutdent");

        Student student = new Student(registrationDto.getEmail(), registrationDto.getPassword());
        student.setFirstName(registrationDto.getFirstName());
        student.setLastName(registrationDto.getLastName());

        return studentRepository.save(student);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);

        Student foundStudent = studentRepository.findByEmail(email); // username will be email
        if (foundStudent == null) {
            throw new UsernameNotFoundException(email);
        }

        String mail = foundStudent.getEmail();
        String password = foundStudent.getPassword();
        return new User(mail, new BCryptPasswordEncoder().encode(password), new ArrayList<>());
    }



    
    

}
