package com.gameinn.review.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private String id;
    private String gameId;
    private String userId;
    private String context;
    /*private int vote;
    private boolean voted;
    private int likeCount;
    private long unixDate;*/
}
