// package com.example.service;

// import com.example.entity.User;
// import com.example.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class UserService {

//     @Autowired
//     private UserRepository userRepository;

//     public List<User> getAllUsers() {
//         return userRepository.findAll();
//     }

//     public User getUserById(Long id) {
//         return userRepository.findById(id).orElse(null);
//     }

//     public User createUser(User user) {
//         return userRepository.save(user);
//     }

//     public User updateUser(Long id, User userDetails) {
//         User user = userRepository.findById(id).orElse(null);
//         if (user != null) {
//             user.setName(userDetails.getName());
//             user.setRole(userDetails.getRole());
//             user.setPassword(userDetails.getPassword());
//             return userRepository.save(user);
//         }
//         return null;
//     }

//     public void deleteUser(Long id) {
//         userRepository.deleteById(id);
//     }

//     public User getUserByRoleAndIdAndPassword(String role, Long id, String password) {
//         return userRepository.findByRoleAndIdAndPassword(role, id, password);
//     }
// }

