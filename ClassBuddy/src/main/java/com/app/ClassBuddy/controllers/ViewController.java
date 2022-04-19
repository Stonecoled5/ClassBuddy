package com.app.ClassBuddy.controllers;

import java.security.Principal;

import com.app.ClassBuddy.database.documents.Course;
import com.app.ClassBuddy.database.documents.Student;
import com.app.ClassBuddy.database.respositories.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ViewController {
    
    @Autowired
    StudentRepository studentRepository;

    
    @GetMapping("/profile")
    public ModelAndView userProf(Principal principal) {
        ModelAndView model = new ModelAndView("ProfilePage.html");
        Student found = studentRepository.findByEmail(principal.getName());
        model.addObject("student", found);
        CourseController controller = new CourseController();
        return model;
    }

    @GetMapping("/profile/edit")
    public ModelAndView editUserProf(Principal principal) {
        ModelAndView model = new ModelAndView("EditProfilePage.html");
        Student found = studentRepository.findByEmail(principal.getName());
        model.addObject("student", found);
        CourseController controller = new CourseController();
        return model;
    }

    @PostMapping("/update")
    public String updateUserProf(Principal principal, @ModelAttribute("update") Student studentUpdateAttributes) {
        System.out.println(studentUpdateAttributes.getEmail());
        String email = studentUpdateAttributes.getEmail();
        String firstName = studentUpdateAttributes.getFirstName();
        String lastName = studentUpdateAttributes.getLastName();
        if (!email.isEmpty()) {
            // update email
        }

        if (!firstName.isEmpty()) {

        }

        if (!lastName.isEmpty()) {
            
        }

        return "redirect:/profile";
    }




    @GetMapping("/explore")
    public static String explorePage(Model model) {
        model.addAttribute("student", new Student());
        return "ExplorePage";
    }

    @PostMapping("/addCourse")
    public static void addCourse(@RequestParam("courseName") String courseName, Principal principal) {
        // will recieve the course as the name... MATH 222 expected
        // ensure it is formatted correctly

        System.out.println("Here");
        System.out.println(principal);
       // return "lo";

       // redirect to this user's session
      // return "redirect:/profile";
    }

}
