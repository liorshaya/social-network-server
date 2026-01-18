package com.server.social_network_server.controllers;

import com.server.social_network_server.response.BasicResponse;
import com.server.social_network_server.response.LikeResponse;
import com.server.social_network_server.utils.DbUtils;
import com.server.social_network_server.utils.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private DbUtils dbUtils;


    @PostMapping("/toggle-like")
    public BasicResponse toggleLike(@RequestParam int userId, @RequestParam int postId) {

        if (!dbUtils.isUserIdExist(userId)) {
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }

        boolean isLikedNow = dbUtils.toggleLike(postId, userId);

        return new LikeResponse(true, null, isLikedNow);
    }

}
