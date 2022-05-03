package com.app.ClassBuddy.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.app.ClassBuddy.database.daos.StudentDAO;
import com.app.ClassBuddy.database.documents.Course;
import com.app.ClassBuddy.database.documents.Student;
import com.app.ClassBuddy.database.documents.Suggestion;
import com.app.ClassBuddy.database.documents.University;
import com.app.ClassBuddy.database.respositories.CourseRepository;
import com.app.ClassBuddy.database.respositories.StudentRepository;
import com.app.ClassBuddy.database.respositories.UniversityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class ViewController {
    
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentDAO dao;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UniversityRepository universityRepository;
    
    @GetMapping("/profile")
    public ModelAndView userProf(Principal principal) {
        ModelAndView model = new ModelAndView("ProfilePage.html");
        Student found = studentRepository.findByEmail(principal.getName());
        model.addObject("student", found);
        return model;
    }

    @GetMapping("/explore")
    public ModelAndView explore(Principal principal) {
        ModelAndView model = new ModelAndView("ExplorePage");
        Student found = getStudent(principal.getName());

        model.addObject("student", found);

        // find matching students, and add them to the model
        findAlikeStudents(found);
        found.sortSuggestionList();
        found.trimSuggestionList(found);


        List<Student> suggestedStudents = new ArrayList<>();
        for (Suggestion s : found.getSuggestedFriendsList()) {
            Student suggested = getStudent(s.getEmail());
            suggestedStudents.add(suggested);
        }

        model.addObject("suggestions", suggestedStudents);
        studentRepository.save(found);

        return model;
    }

    @PostMapping("/visitStudentProfile")
    public ModelAndView visitStudentProfile(@ModelAttribute("profile") String email ) {
        ModelAndView model = new ModelAndView("ProfilePage");
        Student student = studentRepository.findByEmail(email);
        model.addObject("student", student);        
        return model;
    }


    private void findAlikeStudents(Student found) {
        // parse through course repository
        Stream<Student> findIterable = studentRepository.findAllBy();
                
        findIterable.forEach(
            s -> s.findRating(found)
            );
        

    }

    @GetMapping("/profile/edit")
    public ModelAndView editUserProf(Principal principal) {
        ModelAndView model = new ModelAndView("EditProfilePage.html");
        Student found = getStudent(principal.getName());

        // get university majors for list population
        University uni = universityRepository.findByName("UW-Madison");
        ArrayList<String> majors = uni.getMajorList();

        model.addObject("student", found);
        model.addObject("majorList", majors);
        return model;
    }

    private Student getStudent(String email) {
        return studentRepository.findByEmail(email);
    }

    @PostMapping("/update")
    public String updateUserProf(Principal principal, @ModelAttribute("exampleRadios") String year, @ModelAttribute("updateFirstName") String first, @ModelAttribute("updateLastName") String last, @ModelAttribute("updateMajor1") String major1, @ModelAttribute("updateMajor2") String major2) {
        Student student = getStudent(principal.getName());

        student.setYear(year);


        if (major2.equals("N/A")) {
            major2 = "";
        }

        if (first.isEmpty()) {
            first = student.getFirstName();
        }

        if (last.isEmpty()) {
            last = student.getLastName();
        }

        if (major1.isEmpty()) {
            major1 = student.getMajor1();
        }

        if (!first.equals(student.getFirstName())) {
            student.setFirstName(first);
            studentRepository.save(student);
        }

        if (!last.equals(student.getLastName())) {
            student.setLastName(last);
            studentRepository.save(student);
        }

        if (!major1.equals(student.getMajor1())) {
            student.setMajor1(major1);
            studentRepository.save(student);
        }
        

        if (!major2.equals(student.getMajor2())) {
            student.setMajor2(major2);
            studentRepository.save(student);
        }

        return "redirect:/profile";
    }



    @PostMapping("/addCourse")
    public String addCourse(@RequestParam("courseName") String courseName, Principal principal) {
        // will recieve the course as the name... MATH 222 expected
        // ensure it is formatted correctly
        Student student = studentRepository.findByEmail(principal.getName());
        
        
        // try best to format course name correctly and parse out number
        String numberSubstring = courseName.substring(courseName.length() - 3, courseName.length()).trim();
        String abbrevitationSubstring = courseName.substring(0, courseName.length()-3).toUpperCase().trim(); // trim for 2-digit course numbers
        
    
        // find course object
            List<Course> courseToAdd = courseRepository.findCourseByAbbreviation(abbrevitationSubstring);
            for (Course c : courseToAdd) {
                if (c.getNumber().equals(numberSubstring)) {
                    if (student.getSchedule().contains(c)) {
                        return "redirect:/profile?courseExists";
                    }
                    student.addToSchedule(c);
                    studentRepository.save(student);
                    return "redirect:/profile";
                }
            }

            return "redirect:/profile?invalidCourse";

       // redirect to this user's session
    }

    @PostMapping("/deleteCourse")
    public String deleteCourse(@ModelAttribute("courseName") String courseName, Principal principal) {
        Student student = studentRepository.findByEmail(principal.getName());

        // find course to delete
        for (Course c : student.getSchedule()) {
            if (c.getCourseName().equals(courseName)){

                student.getSchedule().remove(c);
                studentRepository.save(student);
                break;
            }
        } 

        return "redirect:/profile";
    }

    @GetMapping("/")
    public ModelAndView aboutPage() {
        ModelAndView model = new ModelAndView("AboutPage");
        return model;
    } 

    @PostMapping("/photos/add")
    public String uploadProfilePicture( @RequestParam("image") MultipartFile file, Model model, Principal principal) {
        try {
            Student found = getStudent(principal.getName());
            System.out.println(file.getOriginalFilename());
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            found.setProfilePicture(fileName);
            studentRepository.save(found);
            
            String uploadDir = "ClassBuddy/src/main/resources/static/profile-pictures";
            FileUploadUtil.saveFile(uploadDir, fileName, file);    
        
        } catch (IOException exception) {
            return "redirect:/profile/edit?fileNotSaved";
        }

        return "redirect:/profile";
    }

    @PostMapping("photos/remove")
    public String removeProfilePicture(Principal principal) {

        Student found = getStudent(principal.getName());
        found.setProfilePicture(null);
        studentRepository.save(found);

        return "redirect:/profile";
    }

    @GetMapping("/searchStudents")
    public ModelAndView searchStudents(Principal principal,  @ModelAttribute("query") String query ) {

        
        // format the query to work nicely with mongoDB's query methods
        ModelAndView model = new ModelAndView("ExplorePage");

        if (query.isEmpty()) {
             return model;
        }
        query = query.substring(0, 1).toUpperCase() + query.substring(1);
        Student found = getStudent(principal.getName());
        ArrayList<Student> suggestedStudents = new ArrayList<>();
        for (Suggestion s : found.getSuggestedFriendsList()) {
            Student suggested = getStudent(s.getEmail());
            suggestedStudents.add(suggested);
        }
        model.addObject("suggestions", suggestedStudents);
        List<Student> studentList = studentRepository.findByFullNameStartingWith(query);
        studentList.addAll(studentRepository.findByLastNameStartingWith(query));
        model.addObject("querySize", studentList.size());
        model.addObject("queriedStudents", studentList);
        model.addObject("suggestions", suggestedStudents);
        
        return model;
    }

}
