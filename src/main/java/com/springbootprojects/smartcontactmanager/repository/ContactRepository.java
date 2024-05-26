package com.springbootprojects.smartcontactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.springbootprojects.smartcontactmanager.entities.Contact;
import com.springbootprojects.smartcontactmanager.entities.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact,String> {

    // custom finder method
    List<Contact> findByUser(User user);

    // custom query method
    @Query("SELECT c from Contact c WHERE c.userId= :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

}
