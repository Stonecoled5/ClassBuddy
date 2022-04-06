package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;

public class Registrationcontroller {
	@RequestMapping("registration")

	public String registrationController() {
		System.out.println("Working");
		return "registration.html";
	}
}
