package com.nutritionalStylist.healthyKitch.service;


import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @author kamal berriga
 *
 */
@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User find(String userName) {
        return userRepository.findByUsername(userName);
    }

    public Optional<User> find(Integer id) {
        return userRepository.findById(id);
    }


    //public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
    public User registerNewUserAccount(User accountDto) {
        User user = new User();
        user.setFullName(accountDto.getFullName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setRole("Standard User");
        return userRepository.save(user);
    }
}