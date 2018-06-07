package com.nutritionalStylist.healthyKitch.controller;

import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.logging.Logger;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4010", maxAge = 3600)
public class AccountController {


    @Autowired
    private UserService userService;

    // request method to create a new account by a guest
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        if (userService.find(newUser.getUsername()) != null) {
            System.out.println("username Already exist " + newUser.getUsername());
            return new ResponseEntity(
                   "user with username " + newUser.getUsername() + "already exist ",
                    HttpStatus.CONFLICT);
        }
        newUser.setRole("USER");

        return new ResponseEntity<User>(userService.save(newUser), HttpStatus.CREATED);
    }

//    // this is the login api/service
//    @CrossOrigin
//    @RequestMapping("/login")
//    public Principal user(Principal principal) {
//        System.out.println("user logged "+principal);
//        return principal;
//    }

//    @PostMapping("/login")
//    String login(
//            final HttpServletRequest request, @RequestParam("username") final String username,
//            @RequestParam("password") final String password) {
//        return authentication
//                .login(username, password)
//                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
//    }

}
