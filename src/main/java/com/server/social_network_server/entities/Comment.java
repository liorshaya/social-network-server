package com.server.social_network_server.entities;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private int userId;
    private int postId;
    private String content;
    private LocalDateTime createdAt;

    private Comment(){

    }

    public Comment(int id, int userId, int postId, LocalDateTime createdAt, String content) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
        this.content = content;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
