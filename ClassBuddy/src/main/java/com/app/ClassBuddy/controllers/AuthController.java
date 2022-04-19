package com.app.ClassBuddy.controllers;

import com.app.ClassBuddy.database.documents.Student;
import com.app.ClassBuddy.database.dtos.StudentRegistrationDto;
import com.app.ClassBuddy.database.respositories.StudentRepository;
import com.app.ClassBuddy.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class AuthController {

    final private String SCHOOL_DOMAIN = "wisc.edu";

    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public String registerStudentAccount(@ModelAttribute("student") StudentRegistrationDto studentRegistrationDto) {
        return validateStudentSignup(studentRegistrationDto);
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("student", new Student());
        return "registration";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "LoginPage";
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute("student") Student student) {
        // get user with email
        System.out.println("HERE IN LOGIN CONTROLLER");
 //       Student found = studentRepository.findByEmail(student.getEmail());
        Student found = studentRepository.findByEmail("email");

        if (found == null) {
            //return "redirect:/login?userNotFound";
        }

        ModelAndView model = new ModelAndView("ProfilePage");
        model.addObject("student", found);
        return model;
        // user found, take them to their profile 
       // ViewController.userProf(found);   
        //return "redirect:/profile";
    }

    private String validateStudentSignup(StudentRegistrationDto studentRegistrationDto) {

        String email = studentRegistrationDto.getEmail();
        String password = studentRegistrationDto.getPassword();
    
        // check if password is over 4 characters
        if (!passwordIsStrong(password)){
            return "redirect:/signup?weakPassword"; 
        }
    
        try {
            if (!emailIsCorrect(email)) {
                return "redirect:/signup?invalidEmail"; 
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // handles not having the '@' sign
            return "redirect:/signup?invalidEmail"; 
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
