package com.matchup.backend.repository;

import com.matchup.backend.model.Ladder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LadderRepository extends MongoRepository<Ladder, String> {
}
