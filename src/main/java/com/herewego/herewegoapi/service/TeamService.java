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

    //TODO: 이후 팀과 리그간의 관계가 맺어진다면 수정해야한다.
    public void updateFavoriteTeam(String email, String teamName, String leagueName) throws ForwardException {
        Optional<Team>optional = Optional.ofNullable(teamRepository.findByTeamName(teamName));

        Team team = optional.orElseThrow(()->{
            LOGGER.debug("Cannot found team by teamName {}",teamName);
           return new ForwardException(ErrorCode.RC400000,"Invalid Team");
        });
        LOGGER.debug("Team Name : {}",team.getTeamName());

        updateUserDetails(email, team.getTeamId());
        return;
    }

    private void updateUserDetails(String email, Integer teamId) throws ForwardException {
        Optional<UserDetails> userDetailsOptional = Optional.ofNullable(userDetailsRepository.findByEmail(email));

        UserDetails userDetails = userDetailsOptional.orElseThrow(()->{
            LOGGER.debug("Cannot found user details by Email {}",email);
            return new ForwardException(ErrorCode.RC400000,"Invalid User Email");
        });

        String favoriteTeamList = userDetails.getFavorites();
        favoriteTeamList += "," + teamId.toString();
        userDetails.setFavorites(favoriteTeamList);
        userDetailsRepository.save(userDetails);
        return;
    }
}
