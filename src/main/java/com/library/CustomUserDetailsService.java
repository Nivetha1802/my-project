// package com.library;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.library.entity.UserEntity;
// import com.library.repository.UserRepository;

// import java.util.Collections;
// import java.util.Optional;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

    
//     private UserRepository userRepository;

//     public CustomUserDetailsService(UserRepository userRepository){
//         this.userRepository = userRepository;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         // Assuming you have a UserEntity class and UserRepository for accessing the database
//         Optional<UserEntity> optionalUser = userRepository.findByName(username);
        
//         if (optionalUser.isEmpty()) {
//             throw new UsernameNotFoundException("User not found with username: " + username);
//         }

//         UserEntity userEntity = optionalUser.get();
//         // Assuming roles are stored as a string in your UserEntity, you can adjust this part
//         SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());

//         return new User(userEntity.getName(), userEntity.getPassword(), Collections.singletonList(authority));
//     }
// }
