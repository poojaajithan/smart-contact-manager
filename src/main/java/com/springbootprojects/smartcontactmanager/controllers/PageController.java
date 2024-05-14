package com.springbootprojects.smartcontactmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("githubrepo", "https://github.com/poojaajithan/");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(){
        System.out.println("About page loading.");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("About services page loading.");
        return "services";
    }
}
