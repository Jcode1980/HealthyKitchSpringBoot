package com.nutritionalStylist.healthyKitch.controller;


import com.nutritionalStylist.healthyKitch.config.security.TokenProvider;
import com.nutritionalStylist.healthyKitch.model.File;
import com.nutritionalStylist.healthyKitch.model.User;
import com.nutritionalStylist.healthyKitch.model.dto.UserDto;
import com.nutritionalStylist.healthyKitch.model.security.AuthToken;
import com.nutritionalStylist.healthyKitch.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


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

    //@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
            @PostMapping(value = "/signIn")
            public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String password) throws AuthenticationException {
                log.info("got here generate");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(email, password);
                Authentication authentication = null;
                try{
                    //log.info("Username: " + usernamePasswordAuthenticationToken.get() +"Credentials:" + usernamePasswordAuthenticationToken.getCredentials());
                    authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
                    log.info("Do i have authentication?? " + authentication);
            //log.info("these are the auth cred: " + authentication.getCredentials().toString());
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);

        AuthToken authToken = new AuthToken(token);
        User user = userService.findByUsernameAndPassword(email, password);
        System.out.println("Found user :" + user.getFullName());
        authToken.setUserDto(UserDto.convertToDto(user));
        System.out.println("returning DTO user: " + UserDto.convertToDto(user));


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth details " + auth.getDetails());
        System.out.println("auth.getPrincipal()" + auth.getPrincipal().getClass().getName());
        System.out.println(auth.getPrincipal());
        UserDetails userDetails =  ((UserDetails)auth.getPrincipal());
        System.out.println("username; " + userDetails.getUsername() + " password: " + userDetails.getPassword());


        return ResponseEntity.ok(authToken);

        //return ResponseEntity.ok(new AuthToken(token));
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
