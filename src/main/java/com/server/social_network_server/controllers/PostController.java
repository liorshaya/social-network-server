package com.server.social_network_server.controllers;

import com.server.social_network_server.dto.PostDto;
import com.server.social_network_server.dto.UserSearchDto;
import com.server.social_network_server.entities.Post;
import com.server.social_network_server.entities.User;
import com.server.social_network_server.response.BasicResponse;
import com.server.social_network_server.response.PostListResponse;
import com.server.social_network_server.response.PostResponse;
import com.server.social_network_server.response.followCountResponse;
import com.server.social_network_server.service.CloudinaryService;
import com.server.social_network_server.utils.DbUtils;
import com.server.social_network_server.utils.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private DbUtils dbUtils;
    @Autowired
    private User user;

    @PostMapping("/create")
    public BasicResponse createPost(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam("userId") int userId
    ) {

        try {
            boolean hasFile = (file != null && !file.isEmpty());
            boolean hasContent = (content != null && !content.trim().isEmpty());

            if (!hasFile && !hasContent) {
                return new BasicResponse(false, Error.ERROR_MISSING_POST_CONTENT);
            }

            if(!dbUtils.isUserIdExist(userId)){
                return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
            }

            if (hasContent && content.length() > 500){
                return new BasicResponse(false, Error.ERROR_TOO_LONG_CONTENT);
            }

            String imageUrl = null;

            if (hasFile) {
                imageUrl = cloudinaryService.uploadImage(file);

                if (imageUrl == null) {
                    return new BasicResponse(false, Error.ERROR_FILE_INVALID);
                }
            }

            String finalContent = hasContent ? content : "";
            Post post = new Post(userId, finalContent, imageUrl);
            PostDto newPost = dbUtils.addPost(post);

            if (newPost != null){
                return new PostResponse(true, null , newPost);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BasicResponse(false, Error.ERROR_UPDATE_FAILED);
    }

    @GetMapping("/get-post")
    public BasicResponse getPost(int postId){
        PostDto post = dbUtils.getPostById(postId);
        if(post != null){
            return new PostResponse(true, null, post);
        }
        return new BasicResponse(false,Error.ERROR_POST_NOT_EXIST);
    }

    @DeleteMapping("/delete-post")
    public BasicResponse deletePost(@RequestParam int postId, @RequestParam int userId){
        PostDto post = dbUtils.getPostById(postId);
        if (post == null) {
            return new BasicResponse(false,Error.ERROR_POST_NOT_EXIST);
        }
        if (post.getAuthorId() != userId) {
            return new BasicResponse(false, Error.ERROR_NO_PERMISSION);
        }
        if (dbUtils.deletePost(postId)){
            return new BasicResponse(true, null);
        }
        return new BasicResponse(false, Error.ERROR_DELETE_POST_FAILED);
    }

    @GetMapping("/get-user-posts")
    public BasicResponse getUserPosts(@RequestParam int targetUserId, @RequestParam int currentUserId,
            @RequestParam(defaultValue = "1") int page) {

        if(!dbUtils.isUserIdExist(targetUserId)){
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }

        List<PostDto> posts = dbUtils.getPostsByUserId(targetUserId, currentUserId, page);

        return new PostListResponse(true, null, posts);
    }

    @GetMapping("/get-post-count")
    public BasicResponse getPostCount(int targetUserId){
        if(!dbUtils.isUserIdExist(targetUserId)) {
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }
        int postCount = dbUtils.getPostCount(targetUserId);
        return new followCountResponse(true, null , postCount );
    }

    @GetMapping("/get-feed")
    public BasicResponse getFeed(int userId, int page){
        if(!dbUtils.isUserIdExist(userId)) {
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }

        List<PostDto> posts = dbUtils.getFeed(userId, page);
        PostListResponse response = new PostListResponse(true , null , posts);

        if (page == 0 || page == 1) {
            List<UserSearchDto> suggestions = dbUtils.getSuggestions(userId);
            response.setSuggestions(suggestions);
        }
        return response;
    }

}