package com.gameinn.user.service.dataTypes;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameLog {
    private Long createDate;
    private Long updateDate;
    private Long startDate;
    private Long stopDate;
    private String gameId;
    private Boolean finished;
}
