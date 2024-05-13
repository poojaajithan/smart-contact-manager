package com.springbootprojects.smartcontactmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("Youtube channel", "Learn coding with John");
        return "home";
    }
}
