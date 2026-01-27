package com.server.social_network_server.dto;

public class MutualFollowersDto {
    private int id;
    private String username;
    private String pictureUrl;
    private String firstName;
    private String lastName;

    public MutualFollowersDto(int id, String firstName, String lastName ,String username, String pictureUrl) {
        this.id = id;
        this.username = username;
        this.pictureUrl = pictureUrl;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
}
