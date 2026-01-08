package com.server.social_network_server.entities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Follow {
    private int id;
    private int followers;
    private int following;
    private LocalDateTime cratedAt;

    public Follow(){
    }

    public Follow(int id, int followers, int following, LocalDateTime cratedAt) {
        this.id = id;
        this.followers = followers;
        this.following = following;
        this.cratedAt = cratedAt;
    }

    public Follow(int id, int followers, int following) {
        this.id = id;
        this.followers = followers;
        this.following = following;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public LocalDateTime getCratedAt() {
        return cratedAt;
    }

    public void setCratedAt(LocalDateTime cratedAt) {
        this.cratedAt = cratedAt;
    }
}
