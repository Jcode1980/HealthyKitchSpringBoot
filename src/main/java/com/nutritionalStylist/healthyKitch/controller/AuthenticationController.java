package com.nutritionalStylist.healthyKitch.controller;


import com.nutritionalStylist.healthyKitch.config.security.TokenProvider;
import com.nutritionalStylist.healthyKitch.model.security.AuthToken;
import com.nutritionalStylist.healthyKitch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    //@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    @PostMapping(value = "/generate-token")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) throws AuthenticationException {
        System.out.println("got here generate");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = null;
        try{
                //System.out.println("Username: " + usernamePasswordAuthenticationToken.get() +"Credentials:" + usernamePasswordAuthenticationToken.getCredentials());
            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            System.out.println("Do i have authentication?? " + authentication);
            //System.out.println("these are the auth cred: " + authentication.getCredentials().toString());
        }catch(Exception e){
            e.printStackTrace();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

}
