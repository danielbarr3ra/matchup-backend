package com.matchup.backend.repository;

import com.matchup.backend.model.Match;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
    public List<Match> findByLadderId(String ladderId);
}
