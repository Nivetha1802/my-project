package com.library.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.library.Dto.User;
import com.library.entity.UserEntity;
import com.library.repository.UserRepository;

@Service
public class UserServiceImpl implements BaseService<UserEntity, Integer>{
    
    
    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
   
    @Override
    public Optional<UserEntity> getById(Integer id) {
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserEntity create(UserEntity user) {
        return userRepository.save(user);
    }

    
    public void saveUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setRole(user.getRole().toUpperCase());
        userEntity.setPassword((user.getPassword()));
        create(userEntity);
    }

    
    public Optional<UserEntity> authenticate(Integer id, String password) {
        Optional<UserEntity> userOptional = getById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return Optional.ofNullable(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> getAll() {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public UserEntity update(Integer id, UserEntity entityDetails) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
