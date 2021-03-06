package com.nutritionalStylist.healthyKitch.model;


/*******************************************************************************
 * 2017, this is the user entity class ,
 * this class implements users details of the spring security framework
 *******************************************************************************/

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.nutritionalStylist.healthyKitch.enums.ImageQualityType;
import com.nutritionalStylist.healthyKitch.model.dto.UserDto;
import com.nutritionalStylist.healthyKitch.model.dto.Views;
import com.nutritionalStylist.healthyKitch.repository.RecipeReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
/**
 * Description of User.
 *
 * @author kamal berriga
 */
@Entity
@Table(name="User")
@Scope("session")
public  class User implements UserDetails {
    /**
     * Description of the property id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Description of the property email.
     */
    @Column(unique = true)
    private String username;
    /**
     * Description of the property password.
     */
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    /**
     * Description of the property role , to grant authority to the user .
     */

    @Column(name = "role")
    private String role;

    @JsonView({Views.ListView.class})
    @Column(name = "given")
    private String given;

    @JsonView({Views.ListView.class})
    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userProfileImageID" )
    protected UserProfileImage userProfileImage;

    private String gender;

    private Integer yearOfBirth;

    private String aboutMe;

    private String websiteURL;

    private String blogURL;

    private String instagramURL;

    private String facebookURL;

//    @Autowired
//    private RecipeReviewRepository reviewRepository;

    public User() {

    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println("which user am i?? " + this.getFullName());
        System.out.println("setting role: " + getRole());
        authorities.add(new SimpleGrantedAuthority(getRole()));
        return authorities;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role +
                ",]";
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return username;
    }

    @JsonIgnore
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return getGiven() + getSurname();
    }

    public Integer getId() {
        return id;
    }

    @JsonIgnore
    public String getGiven() { return given; }

    public void setGiven(String given) { this.given = given; }

    @JsonIgnore
    public String getSurname() {return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public Integer getYearOfBirth() { return yearOfBirth; }

    public void setYearOfBirth(Integer yearOfBirth) { this.yearOfBirth = yearOfBirth; }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String wesiteURL) {
        this.websiteURL = wesiteURL;
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

//    public RecipeReviewRepository getReviewRepository() { return reviewRepository; }
//
//    public void setReviewRepository(RecipeReviewRepository reviewRepository) { this.reviewRepository = reviewRepository; }

    @JsonIgnore
    public Optional<UserProfileImage> getUserProfileImage() { return Optional.ofNullable(userProfileImage);}

    public void setUserProfileImage(UserProfileImage userProfileImage) { this.userProfileImage = userProfileImage; }

//    @JsonView({Views.ListView.class})
//    public Integer profileImageThumbnailID(){
//        if(getUserProfileImage().isPresent()){
//            return getUserProfileImage().get().getThumbnailImage().map(File::getId).orElse(null);
//        }
//        else{
//            return null;
//        }
//    }
//
//    @JsonView({Views.ListView.class})
//    public Integer profileImagePreviewID(){
//        if(getUserProfileImage().isPresent()){
//            return getUserProfileImage().get().getPreviewImage().map(File::getId).orElse(null);
//        }
//        else{
//            return null;
//        }
//    }

    //creating the profile IMage and it's associated files (original, preview and thumbnail)
    public UserProfileImage createUserProfileImage(String fileName) throws Exception{
        UserProfileImage profileImage = new UserProfileImage();
        profileImage.setName(fileName);

        UserImageFile orginalFile = new UserImageFile(fileName, ImageQualityType.ORIGINAL);
        UserImageFile previewFile = new UserImageFile(fileName, ImageQualityType.PREVIEW);
        UserImageFile thumbnailFile = new UserImageFile(fileName, ImageQualityType.THUMBNAIL);
        profileImage.setOrginalImage(orginalFile);
        profileImage.setPreviewImage(previewFile);
        profileImage.setThumbnailImage(thumbnailFile);

        setUserProfileImage(profileImage);

        return profileImage;
    }

    public Optional<RecipeReview> reviewForRecipe(Recipe recipe){
//        List<RecipeReview> reviews = reviewRepository.findByRecipeAndByUserOrderByCreatedDateDesc(recipe, this);
//        System.out.println("thse are the reviews: " + reviews);
//
//        return reviews.stream().findFirst();
        return Optional.empty();
    }

    //used by RecipeDTO
    @JsonView({Views.ListView.class})
    public Integer getUserProfileImageID(){
        return getUserProfileImage().map(UserProfileImage::getId).orElse(null);
    }


    public void updateUserWithUserDto(UserDto userDto){

        setGiven(userDto.getGiven());
        setSurname(userDto.getSurname());
        setEmail(userDto.getEmail());
        setGender(userDto.getGender());
        setYearOfBirth(userDto.getYearOfBirth());
        setAboutMe(userDto.getAboutMe());

        setWebsiteURL(userDto.getWebsiteURL());
        setBlogURL(userDto.getBlogURL());
        setInstagramURL(userDto.getInstagramURL());
        setFacebookURL(userDto.getFacebookURL());

    }


}