package com.gameinn.review.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private String id;
    private String userId;
    private String gameId;
    @Indexed(unique = true)
    private String duplicateCheckVariable;
    private String context;
    private int vote;
    private boolean voted;
    private int likeCount;
    private long createdAt;
    private long updatedAt;
    private List<String> likedUsers;
}
