package com.server.social_network_server.dto;

import com.server.social_network_server.entities.User;

public class UserWithStatus {
    private User user;
    private boolean isFollowing;

    public UserWithStatus(User user, boolean isFollowing) {
        this.user = user;
        this.isFollowing = isFollowing;
    }


    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }


    public boolean getIsFollowing() { return isFollowing; }
    public void setIsFollowing(boolean following) { isFollowing = following; }
}