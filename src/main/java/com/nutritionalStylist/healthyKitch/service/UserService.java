package com.nutritionalStylist.healthyKitch.service;


import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.model.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(Integer id);
    User findOne(String username);
    User findById(Integer id);
    User findByUsernameAndPassword(String username, String password);

    void addImageToUser(User user, MultipartFile file) throws Exception;


}
