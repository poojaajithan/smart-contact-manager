package com.springbootprojects.smartcontactmanager.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        String userName = "";

        if (authentication instanceof OAuth2AuthenticationToken) {

            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = authenticationToken.getAuthorizedClientRegistrationId();
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

            if ("google".equalsIgnoreCase(authorizedClientRegistrationId)) {
                userName = oauthUser.getAttribute("email").toString();
            } 
            else if ("github".equalsIgnoreCase(authorizedClientRegistrationId)) {
                userName = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                        : oauthUser.getAttribute("login").toString() + "@gmail.com";
            }
        }
        else {
            userName = authentication.getName();
        }

        return userName;
    }
}
