package com.herewego.herewegoapi.service;

import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.entity.Team;
import com.herewego.herewegoapi.model.entity.UserDetails;
import com.herewego.herewegoapi.repository.TeamRepository;
import com.herewego.herewegoapi.repository.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    TeamRepository teamRepository;

    public void updateFavoriteTeam(String userId, Integer teamId) throws ForwardException {
        Optional<Team>optional = Optional.ofNullable(teamRepository.findByTeamId(teamId));

        Team team = optional.orElseThrow(()->{
            LOGGER.debug("Cannot found team by teamId {}",teamId);
           return new ForwardException(ErrorCode.RC400000,"Invalid Team");
        });
        LOGGER.debug("Team Name : {}",team.getTeamName());

        updateUserDetails(userId, teamId);
        return;
    }

    private void updateUserDetails(String userId, Integer teamId) throws ForwardException {
        Optional<UserDetails> userDetailsOptional = Optional.ofNullable(userDetailsRepository.findByUserId(userId));

        UserDetails userDetails = userDetailsOptional.orElseGet(()-> {
            LOGGER.debug("Cannot found user details by userId {}", userId);
            return UserDetails.builder()
                    .userId(userId)
                    .favorites("")
                    .build();
        });
        String favoriteTeamList = userDetails.getFavorites();
        favoriteTeamList += "," + teamId.toString();
        userDetails.setFavorites(favoriteTeamList);
        userDetailsRepository.save(userDetails);


        return;
    }
}
