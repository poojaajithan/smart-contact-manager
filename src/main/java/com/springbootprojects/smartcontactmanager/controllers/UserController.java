package com.springbootprojects.smartcontactmanager.controllers;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.springbootprojects.smartcontactmanager.helpers.Helper;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userDashboard(){
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(Authentication authentication){
        String userName = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Username logged in : " + userName);
        return "user/profile";
    }

    // user add contacts page

    // user view contacts

    // user edit contact

    // user delete contact
}
