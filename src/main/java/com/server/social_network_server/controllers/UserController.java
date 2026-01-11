package com.server.social_network_server.controllers;

import com.server.social_network_server.entities.User;
import com.server.social_network_server.response.ProfileResponse;
import com.server.social_network_server.response.UserListResponse;
import com.server.social_network_server.response.BasicResponse;
import com.server.social_network_server.response.UserResponse;
import com.server.social_network_server.utils.DbUtils;
import com.server.social_network_server.utils.Error;
import com.server.social_network_server.utils.HashGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@RestController
public class UserController {

    @Autowired
    private DbUtils dbUtils;

    @Autowired
    private HashGen hashGen;

    //@Autowired
    //private User user;


    @RequestMapping("/allUsers")
    public BasicResponse getAllUsers(){
        return new UserListResponse(true, null , dbUtils.getAllUsers());
    }


    @RequestMapping("/add-user")
    public BasicResponse addUser(String firstName, String lastName, String username, String password){
        if(firstName != null && !firstName.isEmpty()){
            if(lastName != null && !lastName.isEmpty()){
                if(username != null && !username.isEmpty()){
                    if(password != null && !password.isEmpty()){
                        if (!dbUtils.isUsernameExists(username)){

                            //if(user.checkStrongPassword(password)){
                                String hash_password = this.hashGen.hashSHA(username, password);
                                User user = new User(firstName, lastName, username, hash_password);
                                dbUtils.addUser(user);
                                return new BasicResponse(true,null);
                            //}
                            //else {
                             //   return new BasicResponse(false, Error.ERROR_INVALID_PASSWORD);
                            //}

                        } else {
                            return new BasicResponse(false, Error.ERROR_USERNAME_TAKEN);
                        }
                    } else {
                        return new BasicResponse(false, Error.ERROR_MISSING_PASSWORD);
                    }
                } else {
                    return new BasicResponse(false, Error.ERROR_MISSING_USERNAME);
                }
            } else {
                return new BasicResponse(false, Error.ERROR_MISSING_LAST_NAME);
            }
        } else {
            return new BasicResponse(false, Error.ERROR_MISSING_FIRST_NAME);
        }
    }


    @RequestMapping("/login")
    public BasicResponse login(String username, String password){
        if(username == null || username.isEmpty()){
            return new BasicResponse(false, Error.ERROR_MISSING_USERNAME);
        }
        if(password == null || password.isEmpty()){
            return new BasicResponse(false, Error.ERROR_MISSING_PASSWORD);
        }

        String hash_password = this.hashGen.hashSHA(username, password);
        User user = dbUtils.loginCheck(username, hash_password);
        if (user != null){
            return new UserResponse(true, null, user);
        }
        return new BasicResponse(false, Error.ERROR_CREDS_INCORRECT);
    }

    // myUserId - connected user - will change to get from cookies :D
    @RequestMapping("/get-user-info")
    public BasicResponse getUserInfo(int myUserId, int targetUserId){
        User user = dbUtils.getUser(targetUserId);
        if (user != null){
            boolean following = false;
            if (dbUtils.isUserFollowUser(myUserId, targetUserId)){
                following = true;
            }
            int followingCount = dbUtils.getFollowingCount(targetUserId);
            int followersCount = dbUtils.getFollowersCount(targetUserId);
            return new ProfileResponse(true, null, user, following, followingCount, followersCount);
        } else {
            return new BasicResponse(false, Error.ERROR_TARGET_USER_NOT_EXIST);
        }
    }

    @PostMapping("/update-user")
    public BasicResponse updateUser(int userId, String firstName, String lastName, String city, String country, String imageUrl, String description){
        User user = dbUtils.editUser(userId, firstName, lastName, city, country, imageUrl, description);
        if (user != null){
            return new UserResponse(true, null, user);
        }
        return new BasicResponse(false, Error.ERROR_UPDATE_FAILED);
    }


}
