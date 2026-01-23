package com.server.social_network_server.dto;

public class CommentDto {

    private int id;
    private int postId;
    private String content;
    private String createdAt; // או Timestamp

    private int userId;
    private String authorFirstName;
    private String authorLastName;
    private String authorProfileImage;
    private String authorUsername;

    public CommentDto(int id, int postId, String content, String createdAt, int userId, String authorLastName, String authorFirstName, String authorProfileImage, String authorUsername) {
        this.id = id;
        this.postId = postId;
        this.createdAt = createdAt;
        this.content = content;
        this.userId = userId;
        this.authorLastName = authorLastName;
        this.authorFirstName = authorFirstName;
        this.authorProfileImage = authorProfileImage;
        this.authorUsername = authorUsername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getAuthorProfileImage() {
        return authorProfileImage;
    }

    public void setAuthorProfileImage(String authorProfileImage) {
        this.authorProfileImage = authorProfileImage;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String username) {
        this.authorUsername = username;
    }
}