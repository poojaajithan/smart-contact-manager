package com.springbootprojects.smartcontactmanager.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + ":" + value);
        });

        //identify the provider
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = authenticationToken.getAuthorizedClientRegistrationId();
        
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword("dummy");
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setRoleList((List.of(AppConstants.ROLE_USER)));

        if ("google".equalsIgnoreCase(authorizedClientRegistrationId)){
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setProvider(Providers.GOOGLE);
            user.setProviderUserId(oauthUser.getAttribute("name").toString());
            user.setAbout("This account is created using Google");
        }
        else if ("github".equalsIgnoreCase(authorizedClientRegistrationId)){
            String email = oauthUser.getAttribute("email") != null ? 
                                oauthUser.getAttribute("email").toString()
                              : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using GitHub");
        }
        else {
            logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
        }

        // create user and save to database
        User dbUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (dbUser == null){
            userRepository.save(user);
            logger.info("User saved : " + user.getEmail());
        }
        
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
