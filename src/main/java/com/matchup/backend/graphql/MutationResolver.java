package com.matchup.backend.graphql;


import org.springframework.stereotype.Component;

import com.matchup.backend.model.Ladder;
import com.matchup.backend.model.Match;
import com.matchup.backend.model.User;
import com.matchup.backend.repository.LadderRepository;
import com.matchup.backend.repository.MatchRepository;
import com.matchup.backend.repository.UserRepository;

import graphql.kickstart.tools.GraphQLMutationResolver;
import java.util.ArrayList;

@Component
public class MutationResolver implements GraphQLMutationResolver {
    private final UserRepository userRepository;
    private final LadderRepository ladderRepository;
    private final MatchRepository matchRepository;

    public MutationResolver(UserRepository userRepository, LadderRepository ladderRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.ladderRepository = ladderRepository;
        this.matchRepository = matchRepository;
    }

    public User createUser(String name, String email, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        return userRepository.save(user);
    }

    public Ladder createLadder(String name) {
        Ladder ladder = new Ladder();
        ladder.setName(name);
        ladder.setPlayerIds(new ArrayList<>());
        return ladderRepository.save(ladder);
    }

    public Match reportMatch(String ladderId, String player1Id, String player2Id, String score, String winnerId) {
        Match match = new Match();
        match.setLadderId(ladderId);
        match.setPlayer1Id(player1Id);
        match.setPlayer2Id(player2Id);
        match.setScore(score);
        match.setWinnerId(winnerId);
        return matchRepository.save(match);
    }
}
