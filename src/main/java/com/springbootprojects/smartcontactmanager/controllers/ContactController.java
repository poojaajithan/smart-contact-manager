package com.springbootprojects.smartcontactmanager.controllers;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @RequestMapping("/add")
    public String addContactView() {
        return "user/add_contact";
    }
    
}
