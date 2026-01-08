package com.server.social_network_server.entities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String pictureUrl;
    private String description;
    private String city;
    private String country;
    private LocalDateTime createdAt;

    public User(){
    }

    public User(int id, String firstName, String lastName, String username, String pictureUrl, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.pictureUrl = pictureUrl;
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String username , String profileImage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.pictureUrl = profileImage;
    }

    public User( String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String username , String profileImage, String description, String city, String country,  LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.pictureUrl = profileImage;
        this.description = description;
        this.city = city;
        this.country = country;
        this.createdAt = createdAt;

    }
    public User(int id, String firstName, String lastName , String profileImage, String description, String city, String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pictureUrl = profileImage;
        this.description = description;
        this.city = city;
        this.country = country;

    }

    public boolean checkStrongPassword(String password){
        boolean hasUpper = false;
        boolean hasLower= false;
        if(password.length() >= 8){
            for(char c : password.toCharArray()){
                if (Character.isUpperCase(c)){
                    hasUpper = true;
                }else if(Character.isLowerCase(c)){
                    hasLower = true;
                }

                if(hasUpper && hasLower){
                    return true;
                }
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
