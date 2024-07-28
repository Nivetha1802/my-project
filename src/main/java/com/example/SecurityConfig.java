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
//             .authorizeRequests()
//                 .antMatchers("/", "/signup", "/login", "/submitRegistration").permitAll()
//                 .antMatchers("/home").authenticated()
//                 .antMatchers("/studentHomePage").hasAnyRole("STUDENT", "TEACHER")
//                 .antMatchers("/librarianHomePage").hasRole("LIBRARIAN")
//                 .and()
//             .formLogin()
//                 .loginPage("/login")
//                 .defaultSuccessUrl("/home", true)
//                 .permitAll()
//                 .and()
//             .logout()
//                 .logoutUrl("/logout")
//                 .logoutSuccessUrl("/login")
//                 .permitAll();
//     }

//     @Override
//     public void configure(WebSecurity web) throws Exception {
//         web
//             .ignoring()
//             .antMatchers("/css/**", "/js/**", "/images/**");
//     }
// }
