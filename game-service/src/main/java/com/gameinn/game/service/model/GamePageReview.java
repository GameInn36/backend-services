package com.gameinn.game.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamePageReview {
    private String id;
    private User user;
    private String context;
    private int vote;
    private boolean voted;
    private int likeCount;
    private long createdAt;
    private long updatedAt;
    private List<String> likedUsers;

    private GamePageReview(Builder builder){
        this.id = builder.id;
        this.user = builder.user;
        this.context = builder.context;
        this.vote = builder.vote;
        this.voted = builder.voted;
        this.likeCount = builder.likeCount;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.likedUsers = builder.likedUsers;
    }

    @NoArgsConstructor
    public static class Builder{
        private String id;
        private User user;
        private String context;
        private int vote;
        private boolean voted;
        private int likeCount;
        private long createdAt;
        private long updatedAt;
        private List<String> likedUsers;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setContext(String context) {
            this.context = context;
            return this;
        }

        public Builder setVote(int vote) {
            this.vote = vote;
            return this;
        }

        public Builder setVoted(boolean voted) {
            this.voted = voted;
            return this;
        }

        public Builder setLikeCount(int likeCount) {
            this.likeCount = likeCount;
            return this;
        }

        public Builder setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(long updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setLikedUsers(List<String> likedUsers) {
            this.likedUsers = likedUsers;
            return this;
        }

        public GamePageReview build(){
            return new GamePageReview(this);
        }
    }
}
