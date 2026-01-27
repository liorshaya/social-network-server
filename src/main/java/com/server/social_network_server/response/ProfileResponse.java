package com.server.social_network_server.response;

import com.server.social_network_server.dto.MutualFollowersDto;
import com.server.social_network_server.entities.User;

import java.util.List;

public class ProfileResponse extends UserResponse{
    private boolean following;
    private int followingCount;
    private int followersCount;
    private int postCount;
    private List<MutualFollowersDto> mutualFollowers;


    public ProfileResponse(boolean success, Integer errorCode, User user,
                           boolean following, int followingCount, int followersCount, int postCount) {
        super(success, errorCode, user);
        this.following = following;
        this.followingCount = followingCount;
        this.followersCount = followersCount;
        this.postCount = postCount;

    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public List<MutualFollowersDto> getMutualFollowers() {
        return mutualFollowers;
    }

    public void setMutualFollowers(List<MutualFollowersDto> mutualFollowers) {
        this.mutualFollowers = mutualFollowers;
    }

}
