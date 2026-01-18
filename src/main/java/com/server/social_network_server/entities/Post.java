package com.server.social_network_server.entities;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class Post {
    private int id;
    private int userId;
    private String content;
    private String pictureUrl;
    private LocalDateTime createdAt;

    public Post(){
    }

    public Post(int id, String content, int userId, String pictureUrl, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.pictureUrl = pictureUrl;
        this.createdAt = createdAt;
    }

    public Post(int userId, String content , String pictureUrl) {
        this.content = content;
        this.userId = userId;
        this.pictureUrl = pictureUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
