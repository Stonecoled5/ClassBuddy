package com.app.ClassBuddy;

import com.app.ClassBuddy.database.daos.UniversityDAO;
import com.app.ClassBuddy.database.documents.University;
import com.app.ClassBuddy.database.respositories.UniversityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassBuddyApplication {


	public static void main(String[] args) {

		SpringApplication.run(ClassBuddyApplication.class, args);
	
	}




}
