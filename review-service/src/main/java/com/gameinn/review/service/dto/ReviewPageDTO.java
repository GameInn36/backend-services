package com.gameinn.review.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPageDTO {
    List<ReviewReadDTO> mostPopularReviews;
    List<ReviewReadDTO> friendReviews;
}
