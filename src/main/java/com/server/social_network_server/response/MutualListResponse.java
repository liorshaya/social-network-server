package com.server.social_network_server.response;

import com.server.social_network_server.dto.MutualFollowersDto;

import java.util.List;

public class MutualListResponse extends BasicResponse{
    private List<MutualFollowersDto> mutualFollowers;

    public MutualListResponse(List<MutualFollowersDto> mutualFollowers) {
        this.mutualFollowers = mutualFollowers;
    }

    public MutualListResponse(boolean success, Integer errorCode, List<MutualFollowersDto> mutualFollowers) {
        super(success, errorCode);
        this.mutualFollowers = mutualFollowers;
    }

    public List<MutualFollowersDto> getMutualFollowers() {
        return mutualFollowers;
    }

    public void setMutualFollowers(List<MutualFollowersDto> mutualFollowers) {
        this.mutualFollowers = mutualFollowers;
    }
}
