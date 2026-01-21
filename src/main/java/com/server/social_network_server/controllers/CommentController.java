package com.server.social_network_server.controllers;

import com.server.social_network_server.dto.AddCommentRequest;
import com.server.social_network_server.dto.CommentDto;
import com.server.social_network_server.response.BasicResponse;
import com.server.social_network_server.response.CommentResponse;
import com.server.social_network_server.response.CommentsListResponse;
import com.server.social_network_server.utils.DbUtils;
import com.server.social_network_server.utils.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/comment")
public class CommentController {


    @Autowired
    private DbUtils dbUtils;

    @PostMapping("/add-comment")
    public BasicResponse addComment(@RequestBody AddCommentRequest request){

        if(!dbUtils.isUserIdExist(request.getUserId())){
            return new BasicResponse(false, Error.ERROR_USER_NOT_EXIST);
        }

        if(!dbUtils.isPostIdExist(request.getPostId())){
            return new BasicResponse(false, Error.ERROR_POST_NOT_EXIST);
        }

        if(request.getContent().isEmpty() || request.getContent() == null){
            return new BasicResponse(false, Error.ERROR_MISSING_COMMENT_CONTENT);
        }

        CommentDto comment = dbUtils.addComment(request.getUserId(), request.getPostId(), request.getContent());

        if(comment != null){
            return new CommentResponse(true, null , comment);
        }else{
            return new BasicResponse(false, Error.ERROR_ADD_COMMENT_FAILED);
        }
    }

    @GetMapping("/get-post-comments")
    public BasicResponse getPostComments(int postId){
        if(!dbUtils.isPostIdExist(postId)){
            return new BasicResponse(false, Error.ERROR_POST_NOT_EXIST);
        }
        List<CommentDto> commentDtoList = dbUtils.getCommentByPostId(postId);
        return new CommentsListResponse(true, null , commentDtoList);
    }
}
