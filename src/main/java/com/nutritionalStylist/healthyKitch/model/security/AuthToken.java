package com.nutritionalStylist.healthyKitch.model.security;

import com.nutritionalStylist.healthyKitch.model.dto.UserDto;

public class AuthToken {

    private String token;
    private UserDto userDto;

    public AuthToken(){

    }

    public AuthToken(String token, UserDto userDto){
        this.token = token;
        this.userDto = userDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUserDto() { return userDto; }

    public void setUserDto(UserDto userDto) { this.userDto = userDto; }
}
