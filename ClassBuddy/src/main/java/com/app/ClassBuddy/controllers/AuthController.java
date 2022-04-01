package com.app.ClassBuddy.controllers;

import com.app.ClassBuddy.database.documents.Student;
import com.app.ClassBuddy.database.respositories.StudentRepository;
import com.app.ClassBuddy.models.AuthenticationRequest;
import com.app.ClassBuddy.models.AuthenticationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    final private String SCHOOL_DOMAIN = "wisc.edu";

    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    private ResponseEntity<?> addStudent(@RequestBody AuthenticationRequest authenticationRequest){
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();
    
        if (!emailIsCorrect(email)) {
            return ResponseEntity.badRequest().body("You must use your @wisc.edu email!");
        }
        Student student = new Student(email, password);
        // add in checking for bad email (not @wisc)
        
        
        try {
            studentRepository.save(student);
        } catch (Exception e){
            return ResponseEntity.ok(new AuthenticationResponse("Error during signin"));
        }

        
        return ResponseEntity.ok(new AuthenticationResponse("Student signed up successfully under the email " + email + "!"));
    }

    @PostMapping("/login")
    private ResponseEntity<?> signInStudent(@RequestBody AuthenticationRequest authenticationRequest){
        System.out.println("Attempting to log in user");
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();
        
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new AuthenticationResponse("Unable to login user!"));

        }

        return ResponseEntity.ok(new AuthenticationResponse("Successful login!"));

    }


    private boolean emailIsCorrect(String email){
        String[] parts = email.split("@");
        if (!parts[1].equals(SCHOOL_DOMAIN)){
            return false;
        } else {
            return true;
        }
    }
}
