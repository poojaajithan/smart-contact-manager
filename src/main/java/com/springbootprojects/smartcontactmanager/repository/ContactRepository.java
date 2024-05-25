package com.springbootprojects.smartcontactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbootprojects.smartcontactmanager.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact,String> {

}
