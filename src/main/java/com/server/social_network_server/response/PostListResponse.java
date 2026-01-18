package com.server.social_network_server.response;

import com.server.social_network_server.dto.PostDto;

import java.util.List;

public class PostListResponse extends BasicResponse{
    private List<PostDto> posts;

    public PostListResponse(List<PostDto> posts) {
        this.posts = posts;
    }

    public PostListResponse(boolean success, Integer errorCode, List<PostDto> posts) {
        super(success, errorCode);
        this.posts = posts;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }
}
