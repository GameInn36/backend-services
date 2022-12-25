package com.gameinn.game.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    private String id;
    private String name;
    private String cover;
    private String summary;
    private List<String> genres;
    private String publisher;
    private List<String> platforms;
    private double vote;
    private int voteCount;
    private long first_release_date;

    private Game(GameBuilder gameBuilder){
        this.id = gameBuilder.id;
        this.name = gameBuilder.name;
        this.cover = gameBuilder.cover;
        this.summary = gameBuilder.summary;
        this.genres = gameBuilder.genres;
        this.platforms = gameBuilder.platforms;
        this.vote = gameBuilder.vote;
        this.voteCount = gameBuilder.voteCount;
        this.publisher = gameBuilder.publisher;
        this.first_release_date = gameBuilder.first_release_date;
    }

    public static class GameBuilder{
        private String id;
        private String name;
        private String cover;
        private String summary;
        private List<String> genres;
        private String publisher;
        private List<String> platforms;
        private double vote;
        private int voteCount;
        private long first_release_date;

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

        public GameBuilder setCover(String cover) {
            this.cover = cover;
            return this;
        }

        public GameBuilder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public GameBuilder setGenres(List<String> genres) {
            this.genres = genres;
            return this;
        }

        public GameBuilder setPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public GameBuilder setPlatforms(List<String> platforms) {
            this.platforms = platforms;
            return this;
        }

        public GameBuilder setVote(double vote) {
            this.vote = vote;
            return this;
        }

        public GameBuilder setVoteCount(int voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public GameBuilder setFirst_release_date(long first_release_date) {
            this.first_release_date = first_release_date;
            return this;
        }

        public Game build(){
            return new Game(this);
        }
    }
}
