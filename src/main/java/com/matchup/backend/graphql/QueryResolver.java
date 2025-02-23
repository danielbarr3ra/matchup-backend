package com.matchup.backend.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.matchup.backend.model.Ladder;
import com.matchup.backend.model.Match;
import com.matchup.backend.model.User;
import com.matchup.backend.repository.LadderRepository;
import com.matchup.backend.repository.MatchRepository;
import com.matchup.backend.repository.UserRepository;

import java.util.List;

@Controller
public class QueryResolver {
    private final UserRepository userRepository;
    private final LadderRepository ladderRepository;
    private final MatchRepository matchRepository;

    public QueryResolver(UserRepository userRepository, LadderRepository ladderRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.ladderRepository = ladderRepository;
        this.matchRepository = matchRepository;
    }

    @QueryMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @QueryMapping
    public List<Ladder> getLadders() {
        return ladderRepository.findAll();
    }

    @QueryMapping
    public List<Match> getMatches(
        @Argument String ladderId) {
        return matchRepository.findByLadderId(ladderId);
    }
}
