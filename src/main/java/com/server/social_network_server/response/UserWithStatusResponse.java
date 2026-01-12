package com.server.social_network_server.response;

import com.server.social_network_server.dto.UserWithStatus;

import java.util.List;

public class UserWithStatusResponse extends BasicResponse{
    private List<UserWithStatus> userWithStatus;

    public UserWithStatusResponse(List<UserWithStatus> userWithStatus) {
        this.userWithStatus = userWithStatus;
    }

    public UserWithStatusResponse(boolean success, Integer errorCode, List<UserWithStatus> userWithStatus) {
        super(success, errorCode);
        this.userWithStatus = userWithStatus;
    }

    public List<UserWithStatus> getUserWithStatus() {
        return userWithStatus;
    }

    public void setUserWithStatus(List<UserWithStatus> userWithStatus) {
        this.userWithStatus = userWithStatus;
    }
}
