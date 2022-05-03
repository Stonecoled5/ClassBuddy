package com.app.ClassBuddy.database.documents;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Course {
  
  @Id  
  private String Id;
  private String number;
  private String code; // the three digit number at the end of the abbreviation
  private String abbreviation; // the department abbreviation "COMP SCI" or "CHEM"
  private String deptName;
  private String courseName;
  private String teacher;
  private String fullCourseName;

  public Course() {
    courseName = "Course";
    teacher = "teacher";
  }

  /*
   * This constructor is for creating the course object in the CourseParser class
   */
  public Course(String courseName, String number, String code ,String deptName,
      String abbreviation){
    this.courseName = courseName;
    this.number = number;
    this.code = code;
    this.deptName = deptName;
    this.fullCourseName = abbreviation + " " + code;
    }


  public String getId() {
    return this.Id;
  }

  public String getFullCourseName() {
    return fullCourseName;
  }

  public String getNumber() {
    return this.number;
  }

  public String getCode() {
    return this.code;
  }

  public String getAbbreviation() {
    return this.abbreviation;
  }

  public String getDeptName() {
    return this.deptName;
  }

  public String getCourseName() {
    return this.courseName;
  }


  public Course(String name, String teacher) {
    this.courseName = name;
    this.teacher = teacher;
  }

  public String getName() {
    return courseName;
  }

  public String getTeacher() {
    return teacher;
  }

  @Override public String toString(){

    return abbreviation + " " + number + " - " + courseName;
  }

  @Override public boolean equals(Object o) {
    if (o instanceof Course){
      if (courseName.equals(((Course) o).courseName)){
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

}

