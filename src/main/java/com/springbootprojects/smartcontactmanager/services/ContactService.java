package com.springbootprojects.smartcontactmanager.services;

import java.util.List;
import java.util.Optional;

import com.springbootprojects.smartcontactmanager.entities.Contact;

public interface ContactService {
    Contact saveContact(Contact contact);
    Optional<Contact> getContactById(String id);
    Optional<Contact> updateContact(Contact user);
    void deleteContact(String id);
    List<Contact> getAllContacts();
    List<Contact> getByUserId(String userId);
}
