package com.springbootprojects.smartcontactmanager.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springbootprojects.smartcontactmanager.entities.User;
import com.springbootprojects.smartcontactmanager.helpers.Helper;
import com.springbootprojects.smartcontactmanager.services.UserService;

@ControllerAdvice // call this on all routes in this project
public class RootController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @ModelAttribute  // to invoke this function on all methods
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        logger.info("Invoked addLoggedInUserInformation");
        
        if (authentication == null) {
            return;
        }

        String userName = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Username logged in : " + userName);

        User user = service.getUserByEmail(userName);
        logger.info("User details : " + user.getName() + " : " + user.getEmail()); 

        model.addAttribute("loggedInUser", user);
    }
}
