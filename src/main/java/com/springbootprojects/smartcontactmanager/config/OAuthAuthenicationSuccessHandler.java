package com.springbootprojects.smartcontactmanager.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.springbootprojects.smartcontactmanager.entities.Providers;
import com.springbootprojects.smartcontactmanager.entities.User;
import com.springbootprojects.smartcontactmanager.helpers.AppConstants;
import com.springbootprojects.smartcontactmanager.repository.UserRepository;
import java.util.List;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler{

    Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        logger.info("OAuthAuthenicationSuccessHandler");

        // save into database
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        user.getAttributes().forEach((key, value) -> {
            logger.info(key + ":" + value);
        });

        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        String picture = user.getAttribute("picture").toString();

        // create user and save to database
        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setEmail(email);
        user1.setName(name);
        user1.setProfilePic(picture);
        user1.setPassword("dummy");
        user1.setEnabled(true);
        user1.setProvider(Providers.GOOGLE);
        user1.setEmailVerified(true);
        user1.setProviderUserId(name);
        user1.setRoleList((List.of(AppConstants.ROLE_USER)));
        user1.setAbout("This account is created using Google");

        User dbUser = userRepository.findByEmail(email).orElse(null);
        if (dbUser == null){
            userRepository.save(user1);
            logger.info("User saved : " + email);
        }
        
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
