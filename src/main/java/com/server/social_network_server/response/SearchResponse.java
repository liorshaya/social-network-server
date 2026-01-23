package com.server.social_network_server.response;

import com.server.social_network_server.dto.UserSearchDto;

import java.util.List;

public class SearchResponse extends BasicResponse{
    private List<UserSearchDto> userSearchDtos;

    public SearchResponse(List<UserSearchDto> userSearchDtos) {
        this.userSearchDtos = userSearchDtos;
    }

    public SearchResponse(boolean success, Integer errorCode, List<UserSearchDto> userSearchDtos) {
        super(success, errorCode);
        this.userSearchDtos = userSearchDtos;
    }

    public List<UserSearchDto> getUserSearchDtos() {
        return userSearchDtos;
    }
}
