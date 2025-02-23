package com.matchup.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "ladders")
public class Ladder {
    @Id
    private String id;
    private String name;
    private List<String> playerIds; // References User IDs
}
