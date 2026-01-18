package com.server.social_network_server.dto;

import java.time.LocalDateTime;

public class PostDto {
    private int id;
    private String content;
    private String pictureUrl;
    private LocalDateTime createdAt;

    private int authorId;
    private String authorFirstName;
    private String authorLastName;
    private String authorUsername;
    private String authorProfileImage;

    private int likeCount;
    private int commentCount;
    private boolean isLiked;

    public PostDto(){

    }

    public PostDto(int id, String content, String pictureUrl, LocalDateTime createdAt, int authorId, String authorFirstName, String authorLastName, String authorProfileImage, String authorUsername, int likeCount, int commentCount, boolean isLiked) {
        this.id = id;
        this.content = content;
        this.pictureUrl = pictureUrl;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.authorProfileImage = authorProfileImage;
        this.authorUsername = authorUsername;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLiked = isLiked;
    }

    public PostDto(int id, String content, String pictureUrl, LocalDateTime createdAt, int authorId, String authorFirstName, String authorLastName, String authorProfileImage, String authorUsername, int likeCount, int commentCount) {
        this.id = id;
        this.content = content;
        this.pictureUrl = pictureUrl;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.authorProfileImage = authorProfileImage;
        this.authorUsername = authorUsername;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getAuthorProfileImage() {
        return authorProfileImage;
    }

    public void setAuthorProfileImage(String authorProfileImage) {
        this.authorProfileImage = authorProfileImage;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
