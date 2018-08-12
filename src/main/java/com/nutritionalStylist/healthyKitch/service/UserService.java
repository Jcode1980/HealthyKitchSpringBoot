package com.nutritionalStylist.healthyKitch.service;


import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.model.dto.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(Integer id);
    User findOne(String username);
    User findById(Integer id);
}
