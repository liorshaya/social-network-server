package com.server.social_network_server.response;

import com.server.social_network_server.dto.PostDto;

public class PostResponse extends BasicResponse {
    private PostDto post;

    public PostResponse(PostDto post) {
        this.post = post;
    }

    public PostResponse(boolean success, Integer errorCode, PostDto post) {
        super(success, errorCode);
        this.post = post;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }
}
