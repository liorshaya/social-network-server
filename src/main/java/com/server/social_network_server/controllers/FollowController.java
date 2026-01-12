package com.server.social_network_server.controllers;

import com.server.social_network_server.dto.FollowRequests;
import com.server.social_network_server.dto.UserWithStatus;
import com.server.social_network_server.entities.Follow;
import com.server.social_network_server.entities.User;
import com.server.social_network_server.response.BasicResponse;
import com.server.social_network_server.response.UserListResponse;
import com.server.social_network_server.response.UserWithStatusResponse;
import com.server.social_network_server.response.followCountResponse;
import com.server.social_network_server.utils.DbUtils;
import com.server.social_network_server.utils.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FollowController {

    @Autowired
    private DbUtils dbUtils;
    @Autowired
    private User user;
    @Autowired
    private Follow follow;

    @PostMapping("/follow-user")
    public BasicResponse followUser(@RequestBody FollowRequests request){
        // followerUserId = המשתמש שמחובר (אני)
        // targetUserId = המשתמש שאני רוצה לעקוב אחריו (הוא)
        int followerUserId = request.getFollowerUserId();
        int targetUserId = request.getTargetUserId();

        if (followerUserId == targetUserId){
            return new BasicResponse(false, Error.ERROR_SAME_USER);
        }
        if(!dbUtils.isUserIdExist(followerUserId)){
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }
        if(!dbUtils.isUserIdExist(targetUserId)){
            return new BasicResponse(false, Error.ERROR_TARGET_USER_NOT_EXIST);
        }
        if(dbUtils.followUser(followerUserId,targetUserId)){
            return new BasicResponse(true,null);
        }
        return new BasicResponse(false, Error.ERROR_ALREADY_FOLLOWED);

    }

    @PostMapping("/remove-follow")
    public BasicResponse removeFollow(@RequestBody FollowRequests request){
        int followerUserId = request.getFollowerUserId();
        int targetUserId = request.getTargetUserId();

        if (followerUserId == targetUserId){
            return new BasicResponse(false, Error.ERROR_SAME_USER);
        }
        if(!dbUtils.isUserIdExist(followerUserId)){
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }
        if(!dbUtils.isUserIdExist(targetUserId)){
            return new BasicResponse(false, Error.ERROR_TARGET_USER_NOT_EXIST);
        }
        if(dbUtils.removeFollowUser(followerUserId,targetUserId)){
            return new BasicResponse(true, null);
        }
        return new BasicResponse(false, Error.ERROR_USER_NOT_FOLLOWING);
    }

    @RequestMapping("/get-following-users")
    public BasicResponse getFollowingUserList(int currentUserId, int targetUserId){
        if(!dbUtils.isUserIdExist(targetUserId) || !dbUtils.isUserIdExist(currentUserId)){
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }
        List<UserWithStatus> userList = dbUtils.getFollowingUsers(currentUserId, targetUserId);

        return new UserWithStatusResponse(true, null, userList);
    }

    @RequestMapping("/get-followers-users")
    public BasicResponse getFollowersUserList(int currentUserId, int targetUserId){
        if(!dbUtils.isUserIdExist(targetUserId)){
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }
        List<UserWithStatus> userList = dbUtils.getFollowersUsers(currentUserId, targetUserId);

        return new UserWithStatusResponse(true, null, userList);
    }

    @RequestMapping("/get-followers-count")
    public BasicResponse getFollowersCount(int targetUserId){
        if(!dbUtils.isUserIdExist(targetUserId)) {
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }
        int followersCount = dbUtils.getFollowersCount(targetUserId);
        return new followCountResponse(true, null , followersCount );
    }

    @RequestMapping("/get-following-count")
    public BasicResponse getFollowingCount(int targetUserId){
        if(!dbUtils.isUserIdExist(targetUserId)) {
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }
        int followingCount = dbUtils.getFollowingCount(targetUserId);
        return new followCountResponse(true, null , followingCount );
    }

}
