package com.matchup.backend.graphql;


import org.springframework.stereotype.Component;

import com.matchup.backend.model.Ladder;
import com.matchup.backend.model.Match;
import com.matchup.backend.model.User;
import com.matchup.backend.repository.LadderRepository;
import com.matchup.backend.repository.MatchRepository;
import com.matchup.backend.repository.UserRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {
    private final UserRepository userRepository;
    private final LadderRepository ladderRepository;
    private final MatchRepository matchRepository;

    public QueryResolver(UserRepository userRepository, LadderRepository ladderRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.ladderRepository = ladderRepository;
        this.matchRepository = matchRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<Ladder> getLadders() {
        return ladderRepository.findAll();
    }

    public List<Match> getMatches(String ladderId) {
        return matchRepository.findByLadderId(ladderId);
    }
}
