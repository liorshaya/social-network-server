package com.server.social_network_server.response;

public class followCountResponse extends BasicResponse{
    private int followCount;

    public followCountResponse(int followCount) {
        this.followCount = followCount;
    }

    public followCountResponse(boolean success, Integer errorCode, int followCount) {
        super(success, errorCode);
        this.followCount = followCount;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }
}
