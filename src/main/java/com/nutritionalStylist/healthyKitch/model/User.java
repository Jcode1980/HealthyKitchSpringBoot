package com.nutritionalStylist.healthyKitch.model;


/*******************************************************************************
 * 2017, this is the user entity class ,
 * this class implements users details of the spring security framework
 *******************************************************************************/

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

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
    private Long id;
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

    @Column(name = "given")
    private String given;

    @Column(name = "surname")
    private String surname;

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

    public Long getId() {
        return id;
    }

    public String getGiven() { return given; }

    public void setGiven(String given) { this.given = given; }

    public String getSurname() {return surname; }

    public void setSurname(String surname) { this.surname = surname; }
}