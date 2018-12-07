package com.nutritionalStylist.healthyKitch.model.dto;

import com.nutritionalStylist.healthyKitch.model.MeasuredIngredient;
import com.nutritionalStylist.healthyKitch.model.Recipe;
import com.nutritionalStylist.healthyKitch.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class UserDto {
    static private ModelMapper MODEL_MAPPER = new ModelMapper();

    private String username;
    private Integer id;
    private String password;
    private Integer userProfileImageID;
    private String given;
    private String surname;
    private String gender;
    private Integer yearOfBirth;
    private String aboutMe;
    private String websiteURL;
    private String blogURL;
    private String instagramURL;
    private String facebookURL;
    private String email;


//    private String token;

//    public String getToken() { return token; }
//
//    public void setToken(String token) { this.token = token; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    static public UserDto convertToDto(User user) {
        return MODEL_MAPPER.map(user, UserDto.class);
    }

    static public Recipe convertToEntity(RecipeDto recipeDto) {
        return MODEL_MAPPER.map(recipeDto, Recipe.class);
    }

    public Integer getUserProfileImageID() {
        return userProfileImageID;
    }

    public void setUserProfileImageID(Integer userProfileImageID) {
        this.userProfileImageID = userProfileImageID;
    }

    public String getGiven() { return given; }

    public void setGiven(String given) { this.given = given; }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getBlogURL() {
        return blogURL;
    }

    public void setBlogURL(String blogURL) {
        this.blogURL = blogURL;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }
}
