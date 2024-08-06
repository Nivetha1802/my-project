// package com.library.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// import com.library.CustomUserDetailsService;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .authorizeRequests(requests -> requests
//                         .antMatchers("/studentHomePage", "/librarianHomePage").authenticated()
//                         .antMatchers("/lib.jpg").permitAll()
//                         .antMatchers("/**").permitAll())
//                 .formLogin(login -> {
//                     try {
//                         login
//                         .loginPage("/login")
//                         .permitAll()
//                         .defaultSuccessUrl("/studentHomePage", true) // Redirect after successful login
//                         .and()
//                         .logout(logout -> logout
//                                 .permitAll()
//                                 .logoutSuccessUrl("/login?logout"))
//                         .csrf(csrf -> csrf.disable()) // Enable if necessary
//                         .sessionManagement(management -> management
//                                 .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
//                     } catch (Exception e) {
                       
//                         e.printStackTrace();
//                     }
//                 });
           
//         http
//                 .sessionManagement(management -> management
//                         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                         .sessionFixation().newSession());


//         return http.build();
//     }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         return new CustomUserDetailsService();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }
