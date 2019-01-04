package com.nutritionalStylist.healthyKitch.controller;


import com.nutritionalStylist.healthyKitch.config.security.TokenProvider;
import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.model.dto.UserDto;
import com.nutritionalStylist.healthyKitch.model.security.AuthToken;
import com.nutritionalStylist.healthyKitch.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/authenticator")
public class AuthenticationController {
    private Logger log = Logger.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    private AuthToken createAuthenticationToken(String email, String password) throws Exception{
        Authentication authentication;

        //log.info("Username: " + usernamePasswordAuthenticationToken.get() +"Credentials:" + usernamePasswordAuthenticationToken.getCredentials());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(email, password);

        //log.info("Username: " + usernamePasswordAuthenticationToken.get() +"Credentials:" + usernamePasswordAuthenticationToken.getCredentials());
        authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        log.info("Do i have authentication?? " + authentication);
        //log.info("these are the auth cred: " + authentication.getCredentials().toString());
        //log.info("these are the auth cred: " + authentication.getCredentials().toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        log.info("Sending back auth token: " + token );

        User user = userService.findByUsernameAndPassword(email, password);
        return new AuthToken(token, UserDto.convertToDto(user));

    }


    @PostMapping(value = "/signIn")
    public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String password) throws AuthenticationException {
        log.info("got here generate");

        AuthToken authToken;
        try{
            authToken = createAuthenticationToken(email, password);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }



        return ResponseEntity.ok(authToken);

        //return ResponseEntity.ok(new AuthToken(token));
    }


    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto signUpDto) throws AuthenticationException {
        ResponseEntity responseEntity;

//        System.out.println("Goat here: sign-up" + signUpDto.getEmail());
//        System.out.println("Goat here: sign-up" + signUpDto.getGiven());
//        System.out.println("Goat here: sign-up" + signUpDto.getYearOfBirth());


        //Check if user already exists with same email
        User user = userService.findByEmail(signUpDto.getEmail());

        //if user doesn't exist
        if(user == null){
            User newUser = userService.save(signUpDto);

            AuthToken authToken;
            try{
                authToken = createAuthenticationToken(newUser.getEmail(), newUser.getPassword());
            }catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }

            responseEntity = ResponseEntity.ok(authToken);
        }
        //if it already exists, send fail response
        else{
            responseEntity = new ResponseEntity<>("User with Email already exists. Please proceed to Login", HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
//
//    //@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
//    @PostMapping(value = "/signInn")
//    public ResponseEntity<?> signIn() throws AuthenticationException {
//        log.info("got here generate");
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken("john@sqonk.com.au", "games");
//        Authentication authentication = null;
//        try{
//            //log.info("Username: " + usernamePasswordAuthenticationToken.get() +"Credentials:" + usernamePasswordAuthenticationToken.getCredentials());
//            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//            log.info("Do i have authentication?? " + authentication);
//            //log.info("these are the auth cred: " + authentication.getCredentials().toString());
//        }catch(Exception e){
//            e.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final String token = jwtTokenUtil.generateToken(authentication);
//
//        AuthToken authToken = new AuthToken(token);
//        authToken.setUserDto(UserDto.convertToDto(getAuthenticatedUser()));
//        return ResponseEntity.ok(authToken);
//    }

    @GetMapping(value="/token-status")
    public ResponseEntity checkTokenStatus(){
        //FIX ME.. should check the token
        return ResponseEntity.ok().build();
    }



}
