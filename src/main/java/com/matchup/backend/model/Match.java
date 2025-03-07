package com.matchup.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "matches")
public class Match {
    @Id
    private String id;
    private String ladderId;
    private User player1Id;
    private User player2Id;
    private String score;
    private User winnerId;
}
