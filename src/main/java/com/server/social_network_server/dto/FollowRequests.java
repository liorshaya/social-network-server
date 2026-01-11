package com.server.social_network_server.dto;

public class FollowRequests {
    private int followerUserId;
    private int targetUserId;


    public FollowRequests(int followerUserId, int targetUserId) {
        this.followerUserId = followerUserId;
        this.targetUserId = targetUserId;
    }

    public int getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(int followerUserId) {
        this.followerUserId = followerUserId;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
    }
}