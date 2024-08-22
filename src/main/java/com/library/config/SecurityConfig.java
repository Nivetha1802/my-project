// package com.library.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// import com.library.CustomUserDetailsService;
// import com.library.repository.UserRepository;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final UserDetailsService customUserDetailsService;
//     private final UserRepository userRepository;

//     public SecurityConfig(UserDetailsService customUserDetailsService, UserRepository userRepository) {
//         this.customUserDetailsService = customUserDetailsService;
//         this.userRepository = userRepository;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeRequests(requests -> requests
//                         .antMatchers("/css/**", "/js/**", "/images/**").permitAll() // Allow access to static resources
//                         .antMatchers("/studentHomePage").permitAll()
//                         .antMatchers("/search", "/fineDetails", "/renewtable", "/returntable", "/lendtable",
//                                 "/lendDetails")
//                         .hasAnyRole("STUDENT", "TEACHER") // Require 'STUDENT' or 'TEACHER' role for these pages
//                         .antMatchers("/librarianHomePage").authenticated()
//                         .antMatchers("/bookManagement", "/addBook", "/updateBook", "/deleteBook", "/allBooks")
//                         .hasRole("LIBRARIAN") // Require 'LIBRARIAN' role for librarian home page
//                         .anyRequest().permitAll()) // Allow all other requests without authentication
//                 .formLogin(login -> login
//                         .loginPage("/login")
//                         .permitAll())
//                 .logout(logout -> logout
//                         .permitAll())
//                 .httpBasic();// Redirect to login page if session expires
//                 // .rememberMe(rememberMe -> rememberMe
//                 //         .key("uniqueAndSecret"));

//         return http.build();
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         return new CustomUserDetailsService(userRepository);
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
//     }

//     // @Autowired
//     // public void configureGlobal(AuthenticationManagerBuilder auth) throws
//     // Exception {
//     // auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//     // }
// }
