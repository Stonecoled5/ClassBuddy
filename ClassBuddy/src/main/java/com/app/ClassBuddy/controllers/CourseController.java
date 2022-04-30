package com.app.ClassBuddy.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.app.ClassBuddy.database.documents.Course;
import com.app.ClassBuddy.database.respositories.CourseRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CourseController {
  public static final ArrayList<Course> allCourses = new ArrayList<>();
    
    @Autowired
    CourseRepository courseRepository;

    public ResponseEntity<?> addAllCourses() {
        int page = 1;
        int lastPage = 418;
    
        for (int i = page; i <= lastPage; ++i ){
          addAllCoursesHelper(i);
        }
        return ResponseEntity.ok("All courses have been added");
    }


    private void addAllCoursesHelper(int page) {

        String authorizationHeader = "Token token=eaf9597261dd43a58d76440198025337";


        URL url = null;
        try {
          url = new URL("https://api.madgrades.com/v1/courses?page=2");
        } catch (MalformedURLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
    
        HttpURLConnection con = null;
        try {
          con = (HttpURLConnection) url.openConnection();
          con.setRequestMethod("GET");
          con.setRequestProperty("Authorization", "Token token=eaf9597261dd43a58d76440198025337");
          System.out.println( "Querying page: " + page + " " + con.getResponseMessage() + " - " + con.getResponseCode() + "\n");
  
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
    
        BufferedReader reader;
        String line;
        StringBuffer responseContent;
    
        try {
          reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
          responseContent = new StringBuffer();
        } catch (IOException e){
          e.printStackTrace();
          return;
        }
    
        try {
          while ((line = reader.readLine()) != null){
            responseContent.append(line);
          }
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    
        System.out.println(responseContent);
        String responseBody = responseContent.toString();
    
        parse(responseBody);
    
      }
    
      public void parse(String responseBody) throws JSONException {
        JSONObject response = new JSONObject(responseBody);
        JSONArray courses = response.getJSONArray("results");
    
        for (int i = 0; i < courses.length(); i++) {
    
          String uuidStr = null;
          try {
            JSONObject course = courses.getJSONObject(i);
            Integer number =  course.getInt("number");
            String courseName = course.getString("name");
            JSONArray subjects = course.getJSONArray("subjects"); //TODO: get this in the Course constructor
            JSONObject sub = subjects.getJSONObject(0);
            int code = sub.getInt("code");
            String deptName = sub.getString("name");
            String abbv = sub.getString("abbreviation");
    
           Course courseToAdd = new Course(courseName, Integer.toString(number), Integer.toString(code), deptName, abbv);
           
          //  courseRepository.save(courseToAdd);
          //   allCourses.add(courseToAdd);
           // University uni.courses.add(courseToAdd); // courses would be a static field for our
            // university
          } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Found on ID: " + uuidStr);
          }



      }

    }


      public void addCoursesToArray() {
      }
  }
