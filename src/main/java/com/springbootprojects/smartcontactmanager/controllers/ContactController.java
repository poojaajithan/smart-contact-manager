package com.springbootprojects.smartcontactmanager.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.Authentication;
import com.springbootprojects.smartcontactmanager.entities.Contact;
import com.springbootprojects.smartcontactmanager.entities.User;
import com.springbootprojects.smartcontactmanager.forms.ContactForm;
import com.springbootprojects.smartcontactmanager.forms.UserForm;
import com.springbootprojects.smartcontactmanager.helpers.Helper;
import com.springbootprojects.smartcontactmanager.services.ContactService;
import com.springbootprojects.smartcontactmanager.services.UserService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    UserService userService;

    @Autowired
    ContactService contactService;

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value ="/add", method=RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session) {
        if (result.hasErrors()){
            return "user/add_contact";
        }
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setFavorite(contactForm.isFavorite());
        contact.setUser(user);
        
        contactService.saveContact(contact);
        
        return "redirect:/user/contacts/add";
    }
    
}
