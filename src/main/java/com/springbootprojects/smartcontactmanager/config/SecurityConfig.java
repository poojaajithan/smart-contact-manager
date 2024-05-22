package com.springbootprojects.smartcontactmanager.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.springbootprojects.smartcontactmanager.services.impl.SecurityCustomUserDetailService;
import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

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

    @Autowired
    OAuthAuthenicationSuccessHandler handler;

    // configuration of authentication provider for spring security (using database)
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
        //httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate"); // This means login page is designed at login.html, but on click of submit button /authenticate needs to be invoked
            formLogin.defaultSuccessUrl("/user/dashboard"); //TODo - check why this did not work with success default url
            //formLogin.failureForwardUrl("/login?error=true"); // does not with incorrect creds
            formLogin.usernameParameter("email"); // look for name attribute in login.html for email
            formLogin.passwordParameter("password");
            
            /*formLogin.failureHandler(new AuthenticationFailureHandler() {
                @Override
                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			                                        AuthenticationException exception) throws IOException, ServletException{
                        throw new UnsupportedOperationException("Unimplemeted method on authentication failure");
                }
            });

            formLogin.successHandler(new AuthenticationSuccessHandler(){
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			                                    Authentication authentication) throws IOException, ServletException{
                    throw new UnsupportedOperationException("Unimplemeted method on authentication success");
                }
            });*/ 
        }); 

        httpSecurity.csrf(AbstractHttpConfigurer::disable); // else logout wont work, check inside logoutUrl

        // oauth configuartions
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }
}
