package com.server.social_network_server.response;

public class LikeResponse extends BasicResponse{
    private boolean isLiked;

    public LikeResponse(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public LikeResponse(boolean success, Integer errorCode, boolean isLiked) {
        super(success, errorCode);
        this.isLiked = isLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
