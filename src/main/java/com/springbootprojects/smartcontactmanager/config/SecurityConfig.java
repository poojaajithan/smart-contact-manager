package com.springbootprojects.smartcontactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
public class SecurityConfig {

    // create user and login using in-memory service
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = org.springframework.security.core.userdetails.User
                                                                            .withDefaultPasswordEncoder()
                                                                            .username("admin")
                                                                            .password("admin123")
                                                                            .roles("ADMIN", "USER")
                                                                            .build();
        UserDetails user2 = org.springframework.security.core.userdetails.User
                                                                            .withDefaultPasswordEncoder()
                                                                            .username("john")
                                                                            .password("john123")
                                                                            .build();

        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);

        return inMemoryUserDetailsManager;

    }
}
