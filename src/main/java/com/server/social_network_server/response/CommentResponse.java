package com.server.social_network_server.response;

import com.server.social_network_server.dto.CommentDto;

public class CommentResponse extends BasicResponse{
    private CommentDto comment;

    public CommentResponse(CommentDto comment) {
        this.comment = comment;
    }

    public CommentResponse(boolean success, Integer errorCode, CommentDto comment) {
        super(success, errorCode);
        this.comment = comment;
    }

    public CommentDto getComment() {
        return comment;
    }

    public void setComment(CommentDto comment) {
        this.comment = comment;
    }
}
