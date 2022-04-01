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
    
        // check if password is over 4 characters
        if (!passwordIsStrong(password)){
            return ResponseEntity.badRequest().body("Ensure your password is over 4 characters long."); 
        }
    
        try {
            if (!emailIsCorrect(email)) {
                return ResponseEntity.badRequest().body("You must use your @wisc.edu email!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // handles not having the '@' sign
            return ResponseEntity.badRequest().body("Please enter a valid email address!");
        }

        // check if email exists
        if (emailExists(email)){
            return ResponseEntity.badRequest().body("Your email already exists!");
        }

        Student student = new Student(email, password);
        
        
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
            System.out.println("trying");        

        } catch (Exception e) {
            System.out.println("Caught");
            return ResponseEntity.badRequest().body("Unable to login user!");

        }

        return ResponseEntity.ok(new AuthenticationResponse("Successful login!"));

    }


    private boolean emailIsCorrect(String email) throws ArrayIndexOutOfBoundsException {
        String[] parts = email.split("@");
        if (!parts[1].equals(SCHOOL_DOMAIN)){
            return false;
        } else {
            return true;
        }
    }

    private boolean emailExists(String email) {
        return studentRepository.findByEmail(email) != null;
    }

    private boolean passwordIsStrong(String pass) {
        return pass.length() > 4;
    }

}
