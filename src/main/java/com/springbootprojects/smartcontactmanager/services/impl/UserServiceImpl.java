package com.springbootprojects.smartcontactmanager.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.springbootprojects.smartcontactmanager.entities.User;
import com.springbootprojects.smartcontactmanager.services.UserService;
import com.springbootprojects.smartcontactmanager.repository.UserRepository;
import com.springbootprojects.smartcontactmanager.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user){
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(String id){
        return userRepository.findById(id);
    }
    
    @Override
    public Optional<User> updateUser(User user){
       User updateUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
       updateUser.setName(user.getName());
       updateUser.setEmail(user.getEmail());
       updateUser.setPassword(user.getPassword());
       updateUser.setPhoneNumber(user.getPhoneNumber());
       updateUser.setAbout(user.getAbout());
       updateUser.setProfilePic(user.getProfilePic());
       updateUser.setEnabled(user.isEnabled());
       updateUser.setEmailVerified(user.isEmailVerified());
       updateUser.setPhoneVerified(user.isPhoneVerified());
       updateUser.setProvider(user.getProvider());
       updateUser.setProviderUserId(user.getProviderUserId());

       User savedUser = userRepository.save(updateUser);
       return Optional.ofNullable(savedUser);
    }

    @Override
    public void deleteUser(String userId){
        User deleteUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(deleteUser);
    }

    @Override
    public boolean isUserExist(String userId){
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email){
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
