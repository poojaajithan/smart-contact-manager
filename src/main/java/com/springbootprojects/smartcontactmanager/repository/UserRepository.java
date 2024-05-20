package com.springbootprojects.smartcontactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springbootprojects.smartcontactmanager.entities.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    //pattern for Spring JPA is findBy<fieldName>
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
}
