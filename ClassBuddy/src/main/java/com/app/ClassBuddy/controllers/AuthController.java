package com.app.ClassBuddy.controllers;

import com.app.ClassBuddy.database.daos.StudentDAO;
import com.app.ClassBuddy.database.documents.Student;
import com.app.ClassBuddy.database.dtos.StudentRegistrationDto;
import com.app.ClassBuddy.database.respositories.StudentRepository;
import com.app.ClassBuddy.models.AuthenticationRequest;
import com.app.ClassBuddy.models.AuthenticationResponse;
import com.app.ClassBuddy.services.UserService;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class AuthController {

    final private String SCHOOL_DOMAIN = "wisc.edu";

    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public String registerStudentAccount(@ModelAttribute("student") StudentRegistrationDto studentRegistrationDto) {
        return validateStudentSignup(studentRegistrationDto);
    }

    @GetMapping("/signup")
    public String showRegistartaionForm(Model model) {
        model.addAttribute("student", new Student());
        return "registration";
    }

    private String validateStudentSignup(StudentRegistrationDto studentRegistrationDto) {

        String email = studentRegistrationDto.getEmail();
        String password = studentRegistrationDto.getPassword();
    
        // check if password is over 4 characters
        if (!passwordIsStrong(password)){
            return "reidrect:/signup?weakPassword"; 
        }
    
        try {
            if (!emailIsCorrect(email)) {
                return "reidrect:/signup?invalidEmail"; 
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // handles not having the '@' sign
            return "reidrect:/signup?invalidEmail"; 
        }

        // check if email exists
        if (emailExists(email)){
            return "redirect:/signup?emailExists"; 
        }        
        
    

        userService.save(studentRegistrationDto);
        return "redirect:/signup?success";

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
        return pass.length() > 5;
    }

}
