package com.app.ClassBuddy.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class ExploreController {
    

    @GetMapping("/explore")
    public ModelAndView explore(Principal principal) {
        System.out.println("HEre");
        ModelAndView model = new ModelAndView("ExplorePage");
        System.out.println(principal.getName());
        
        return model;
    }

}
