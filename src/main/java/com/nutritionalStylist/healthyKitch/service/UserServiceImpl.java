package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.config.security.NoEncoder;
import com.nutritionalStylist.healthyKitch.image.ImageHandler;
import com.nutritionalStylist.healthyKitch.model.*;
import com.nutritionalStylist.healthyKitch.model.dto.UserDto;
import com.nutritionalStylist.healthyKitch.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    private Logger log = Logger.getLogger(UserDetailsService.class);

    @Autowired
    private ImageHandler imageHandler;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private NoEncoder bcryptEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("loadUserByUsername : gotss here: " + username);
        //Thread.dumpStack();


        User user = userRepository.findByUsername(username);
        //Email is used to log in user
        //User user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        user.getRole().forEach(role -> {
//            //authorities.add(new SimpleGrantedAuthority(role.getName()));
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//        });
//        return authorities;
        return new HashSet<SimpleGrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findOne(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User save(UserDto userDto) {
        //User newUser = new User();
        //newUser.setUsername(user.getUsername());
        //newUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        System.out.println("Year of Birth dto: " + userDto.getYearOfBirth());
        User newUser = UserDto.convertToEntity(userDto, bcryptEncoder);
        System.out.println("Year of Birth after convert: " + newUser.getYearOfBirth());
        return userRepository.save(newUser);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public void addImageToUser(User user, MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        log.info("user profile being uploaded: " + fileName);
//        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeID);
//        Recipe theRecipe = recipeOptional.get();

        //two step process which first creates the file objects then the image
        //handler will save the files... This seems pretty dodgey.. might
        //need to revise this in the future.
        UserProfileImage userProfileImage = user.createUserProfileImage(fileName);
        userRepository.save(user);

        imageHandler.processAndSaveFile(userProfileImage, file);
    }
}
