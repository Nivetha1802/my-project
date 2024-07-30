// package com.example;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.builders.WebSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http
//                 .httpBasic(basic -> basic.disable())
//                 .csrf(csrf -> csrf
//                         .disable()) // Disable CSRF protection
//                 .authorizeRequests(requests -> requests
//                         .antMatchers("/", "/signup", "/login", "/submitRegistration").permitAll() // Allow these URLs without authentication
//                         .antMatchers("/home").authenticated() // Require authentication for /home
//                         .antMatchers("/studentHomePage").hasAnyRole("STUDENT", "TEACHER") // Require STUDENT or TEACHER role for /studentHomePage
//                         .antMatchers("/librarianHomePage").hasRole("LIBRARIAN") // Require LIBRARIAN role for /librarianHomePage
//                         .anyRequest().authenticated())
//                 .formLogin(login -> login
//                         .loginPage("/login") // Custom login page
//                         .defaultSuccessUrl("/home", true) // Redirect to /home on successful login
//                         .permitAll())
//                 .logout(logout -> logout
//                         .logoutUrl("/logout") // URL for logout
//                         .logoutSuccessUrl("/login") // Redirect to /login after logout
//                         .permitAll());
//     }

//     @Override
//     public void configure(WebSecurity web) throws Exception {
//         web
//             .ignoring()
//             .antMatchers("/css/**", "/js/**", "/images/**"); // Ignore static resources
//     }
// }
