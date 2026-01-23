package com.server.social_network_server.dto;

public class UserSearchDto {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String pictureUrl;

    public UserSearchDto(int id, String firstName, String lastName, String username, String pictureUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.pictureUrl = pictureUrl;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getPictureUrl() { return pictureUrl; }
}