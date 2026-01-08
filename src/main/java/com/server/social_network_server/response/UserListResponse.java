package com.server.social_network_server.response;

import com.server.social_network_server.entities.User;

import java.util.List;

public class UserListResponse extends BasicResponse {
    private List<User> userList;

    public UserListResponse(boolean success, Integer errorCode, List<User> userList) {
        super(success, errorCode);
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
