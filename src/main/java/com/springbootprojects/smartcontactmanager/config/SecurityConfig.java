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
import com.springbootprojects.smartcontactmanager.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    // create user and login using in-memory service
    /*@Bean
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
    }*/

    @Autowired
    SecurityCustomUserDetailService userDetailService;

    // configuraiton of authentication providerfor spring security
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //configuration
        httpSecurity.authorizeHttpRequests((authorizeHttpRequests) -> {
                                            //authorizeHttpRequests.requestMatchers("/home", "/register", "/services").permitAll());
                                            authorizeHttpRequests.requestMatchers("/user/**").authenticated();
                                            authorizeHttpRequests.anyRequest().permitAll();
                    });
        httpSecurity.formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
