package com.springbootprojects.smartcontactmanager.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootprojects.smartcontactmanager.entities.Contact;
import com.springbootprojects.smartcontactmanager.exceptions.ResourceNotFoundException;
import com.springbootprojects.smartcontactmanager.repository.ContactRepository;
import com.springbootprojects.smartcontactmanager.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        return contactRepository.save(contact);
    }

    @Override
    public Optional<Contact> getContactById(String id) {
        return contactRepository.findById(id);
        
    }

    @Override
    public Optional<Contact> updateContact(Contact user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateContact'");
    }

    @Override
    public void deleteContact(String contactId) {
        Contact deleteContact = contactRepository.findById(contactId).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        contactRepository.delete(deleteContact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepository.findByUserId(userId);
    }

}
