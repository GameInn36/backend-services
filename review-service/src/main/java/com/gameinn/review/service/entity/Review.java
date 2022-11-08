package com.gameinn.review.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String id;
    private String gameId;
    private String userId;
    private String context;
    /*private int vote;
    private boolean voted;
    private int likeCount;
    private long unixDate;*/
}
