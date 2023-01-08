package com.gameinn.review.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateUpdateDTO {
    @NotNull
    private String userId;
    @NotNull
    private String gameId;
    @Size(min=1)
    private String context;
    @Min(value = 1)
    @Max(value = 5)
    private int vote;
    private boolean voted;
}
