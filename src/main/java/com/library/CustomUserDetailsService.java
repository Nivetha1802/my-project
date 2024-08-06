// package com.library;


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.library.entity.UserEntity;
// import com.library.repository.UserRepository;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         UserEntity user = userRepository.findByName(username);
//         if (user == null) {
//             throw new UsernameNotFoundException("User not found");
//         }
//      return org.springframework.security.core.userdetails.User.withUsername(user.getName())
//     .password(user.getPassword())
//     .roles(user.getRole())
//     .build();
// }
// }

