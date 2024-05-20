package com.springbootprojects.smartcontactmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import com.springbootprojects.smartcontactmanager.entities.User;
import com.springbootprojects.smartcontactmanager.forms.UserForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.springbootprojects.smartcontactmanager.services.UserService;

@Controller
public class PageController {

    @Autowired
    private UserService userService;
    
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

    @GetMapping("/contact")
    public String contact(){
        System.out.println("About Contact page loading.");
        return "contact";
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("About Login page loading.");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //processing form
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm){
        System.out.println("Processing registration");
        // fetch form data
        System.out.println("User Form : " + userForm);

        // validate form data

        // save to database
       
        User user = User.builder()
                        .name(userForm.getName())
                        .email(userForm.getEmail())
                        .password(userForm.getPassword())
                        .about(userForm.getAbout())
                        .phoneNumber(userForm.getPhoneNumber())
                        .profilePic("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.shutterstock.com%2Fsearch%2Fdefault-avatar&psig=AOvVaw382ZW576C6SyQx7hhGfxk_&ust=1716315738842000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCIC7gMTsnIYDFQAAAAAdAAAAABAE")
                        .build();

        User savedUser = userService.saveUser(user);
        System.out.println("Saved user : " + savedUser);
        return "redirect:/register";
    }
}
