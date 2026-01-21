package com.server.social_network_server.response;

import com.server.social_network_server.dto.CommentDto;

import java.util.List;

public class CommentsListResponse extends BasicResponse{
    private List<CommentDto> commentDtoList;

    public CommentsListResponse(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }

    public CommentsListResponse(boolean success, Integer errorCode, List<CommentDto> commentDtoList) {
        super(success, errorCode);
        this.commentDtoList = commentDtoList;
    }

    public List<CommentDto> getCommentDtoList() {
        return commentDtoList;
    }

    public void setCommentDtoList(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }
}
