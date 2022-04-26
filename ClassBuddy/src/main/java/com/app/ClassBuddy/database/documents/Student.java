package com.app.ClassBuddy.database.documents;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.servlet.mvc.method.annotation.ContinuationHandlerMethodArgumentResolver;

@Document
public class Student {

    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int year; // 1 = Fresh. 2 = Soph. 3 = Jr. 4 = Sr. 5 = Other
    private String username;
    private ArrayList<Course> schedule;
    private ArrayList<Student> suggestedFriends;
    private String major1;
    private String major2;
    private String profilePicture;
    private String profilePictureLocation;
    final static private String PROFILE_PICTURE_LOCATION = "profile-pictures";



    public Student(String email, String password){
        this.email = email;
        this.password = password;
        this.schedule = new ArrayList<>();
        this.suggestedFriends = new ArrayList<>();
    }

    public Student() {

    }




    public String getProfilePicture() {
        return PROFILE_PICTURE_LOCATION + "/" + profilePicture;
    }

    @Transient
    public String getPhotosImagePath() {
        if (profilePicture == null || profilePicture.isEmpty()) return null;
         
        return getProfilePicture();
    }


    public void setProfilePicture(String profilePicture) {
        profilePictureLocation = PROFILE_PICTURE_LOCATION + "/" + profilePicture;
        this.profilePicture = profilePicture;
    }


    public String getMajor1() {
        return this.major1;
    }

    public void setMajor1(String major1) {
        this.major1 = major1;
    }

    public String getMajor2() {
        return this.major2;
    }

    public void setMajor2(String major2) {
        this.major2 = major2;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Course> getSchedule() {
        return this.schedule;
    }

    public void setSchedule(ArrayList<Course> schedule) {
        this.schedule = schedule;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addToSchedule(List<Course> courses) {
        for (Course c : courses){
            addToSchedule(c);
        }
    }

    public void addToSchedule(Course course) {
        schedule.add(course);
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

    public void findRating(Student found) {
        if (found.email.equals(this.email)) {
            return;
        }

        // points will be updated throughout this method, at the end, if the points are greater than the top 5, "this" will be added to found's recommended students
        int points = 0;

        // find common majors
        if (found.major1 != null && this.major1 != null && major1.equals(this.major1)) {
            points += 20;
        }

        /*
         * Iterating though the comparing student's schedule, we will compare their classes.
         *      + 15 For an exact course match
         *      + 10 For a course with the same department and course number within 100
         *      + 5  For a course with the same department 
         */ 
        for (Course c : this.schedule) {
            if (found.schedule.contains(c)) {
                points += 15;
            } else {
                for (Course foundCourse : found.schedule ) {
                    if (foundCourse.getDeptName().equals(c.getDeptName())) {
                        if (Math.abs(Integer.parseInt(foundCourse.getNumber()) - (Integer.parseInt(c.getNumber()))) <= 100) {
                            points += 10;
                        } else {
                            points += 5;
                        }
                    }
                }

             }

      }
      // End iteration

      if (isSuggested(points)) {
        found.suggestedFriends.add(this);
    }




    }

    private boolean isSuggested(int points) {
        return false;
    }
}