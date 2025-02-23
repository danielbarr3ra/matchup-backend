package com.matchup.backend.graphql;


import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.matchup.backend.model.Ladder;
import com.matchup.backend.model.Match;
import com.matchup.backend.model.User;
import com.matchup.backend.repository.LadderRepository;
import com.matchup.backend.repository.MatchRepository;
import com.matchup.backend.repository.UserRepository;

import java.util.ArrayList;

@Controller
public class MutationResolver {
    private final UserRepository userRepository;
    private final LadderRepository ladderRepository;
    private final MatchRepository matchRepository;

    public MutationResolver(UserRepository userRepository, LadderRepository ladderRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.ladderRepository = ladderRepository;
        this.matchRepository = matchRepository;
    }

    @MutationMapping
    public User createUser(
        @Argument String name, 
        @Argument String email, 
        @Argument String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        return userRepository.save(user);
    }

    @MutationMapping
    public Ladder createLadder(
        @Argument String name) {
        Ladder ladder = new Ladder();
        ladder.setName(name);
        ladder.setPlayerIds(new ArrayList<>());
        return ladderRepository.save(ladder);
    }

    @MutationMapping
    public Match reportMatch(
        @Argument String ladderId, 
        @Argument String player1Id, 
        @Argument String player2Id, 
        @Argument String score, 
        @Argument String winnerId) {
        Match match = new Match();

        // validate users function should go here...
        match.setLadderId(ladderId);
        match.setPlayer1Id(userRepository.findById(player1Id).get());
        match.setPlayer2Id(userRepository.findById(player2Id).get());
        match.setScore(score);
        match.setWinnerId(userRepository.findById(player2Id).get());
        return matchRepository.save(match);
    }
}
