package com.app.ClassBuddy.database.documents;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.client.model.geojson.Point;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Student {

    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private String year; // 1 = Fresh. 2 = Soph. 3 = Jr. 4 = Sr. 5 = Other
    private ArrayList<Course> schedule;
    private List<Suggestion> suggestedFriends; // will hold the email of suggestions
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

    public void setFullName() {
        fullName = firstName + " " + lastName;
    }

    public String getFullName() {
        return fullName;
    }


    public void addSuggestedFriendsList(Student student, Integer score) {

        System.out.println("adding for " + email);
        student.suggestedFriends.add(new Suggestion(this.getEmail(), score));
    }

    public void setSuggestedFriendsList(Student student, Integer score) {
        System.out.println("here");
        System.out.println(student.getEmail());
        for (int i = 0; i < student.suggestedFriends.size(); ++i) {
            if (this.getEmail().equals(student.suggestedFriends.get(i).getEmail())){
                // already in list, update 
                // student.suggestedFriends.get(i).setScore(score);
                student.suggestedFriends.set(i, new Suggestion(email, score));
                // return;
            }
        }

    }

    public List<Suggestion> getSuggestedFriendsList() {
        return suggestedFriends;
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
        setFullName();
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
        setFullName();
    }
    public void setYear(String year) {
        this.year = year;
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

    public String getYear() {
        return this.year;
    }
    private String password;

    public String getId() {
        return this.id;
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
        if (found.major1 != null && this.major1 != null && found.major1.equals(this.major1)) {
            System.out.println("Same major as " + this.email);
            points += 20;
        }

        /*
         * Iterating though the comparing student's schedule, we will compare their classes.
         *      + 15 For an exact course match
         *      + 10 For a course with the same department and course number within 100
         *      + 5  For a course with the same department 
         */ 
        for (Course c : found.schedule) {
            System.out.println("comparing aginst " + c.getCourseName());
            System.out.println("looping through" + email + "s schedule");
            if (this.schedule.contains(c)) {
                System.out.println("same course for " + email + " " + c.getAbbreviation() + c.getCourseName());
                points += 15;
            } else {
                for (Course foundCourse : this.schedule ) {
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

      // this is where we determine to add the student to the list, or just edit their score
        boolean hasStudent = false;
      for (Suggestion s : found.suggestedFriends) {
          if (s.getEmail().equals(this.email)) {
            hasStudent = true;
        }
      }
      System.out.println(hasStudent);

      if (hasStudent) {
          setSuggestedFriendsList(found, points);
      } else {
          addSuggestedFriendsList(found, points);
      }

    }
    

    public void trimSuggestionList(Student found) { 
        if (found.getSuggestedFriendsList().size() < 5) {
            return;
        }
        found.assignSuggestedFriendsList(found.getSuggestedFriendsList().subList(0, 4));;
    }

    public void assignSuggestedFriendsList(List<Suggestion> suggestions) {
        this.suggestedFriends = suggestions;
    }

        
    public void sortSuggestionList() {
        Collections.sort(suggestedFriends, new SuggestionComparator());
    }
}