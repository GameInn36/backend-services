package com.gameinn.game.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    private String id;
    private String name;
    private int year;
    private Binary cover;
    private String summary;
    private String[] categories;
    private String studio;
    private String[] platforms;
    private float vote;
    private int voteCount;
    private long releaseDate;

    private Game(GameBuilder gameBuilder){
        this.id = gameBuilder.id;
        this.name = gameBuilder.name;
        this.year = gameBuilder.year;
        this.cover = gameBuilder.cover;
        this.summary = gameBuilder.summary;
        this.categories = gameBuilder.categories;
        this.studio = gameBuilder.studio;
        this.platforms = gameBuilder.platforms;
        this.vote = gameBuilder.vote;
        this.voteCount = gameBuilder.voteCount;
        this.releaseDate = gameBuilder.releaseDate;
    }

    public static class GameBuilder{
        private String id;
        private String name;
        private int year;
        private Binary cover;
        private String summary;
        private String[] categories;
        private String studio;
        private String[] platforms;
        private float vote;
        private int voteCount;
        private long releaseDate;

        public GameBuilder(String name){
            this.name = name;
        }
        public GameBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public GameBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public GameBuilder setYear(int year) {
            this.year = year;
            return this;
        }

        public GameBuilder setCover(Binary cover) {
            this.cover = cover;
            return this;
        }

        public GameBuilder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public GameBuilder setCategories(String[] categories) {
            this.categories = categories;
            return this;
        }

        public GameBuilder setStudio(String studio) {
            this.studio = studio;
            return this;
        }

        public GameBuilder setPlatforms(String[] platforms) {
            this.platforms = platforms;
            return this;
        }

        public GameBuilder setVote(float vote) {
            this.vote = vote;
            return this;
        }

        public GameBuilder setVoteCount(int voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public GameBuilder setReleaseDate(long releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Game build(){
            return new Game(this);
        }
    }
}
