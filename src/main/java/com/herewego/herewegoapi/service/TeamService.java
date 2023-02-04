package com.herewego.herewegoapi.service;

import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.entity.Team;
import com.herewego.herewegoapi.model.entity.UserDetails;
import com.herewego.herewegoapi.model.response.TeamInfoVO;
import com.herewego.herewegoapi.repository.TeamRepository;
import com.herewego.herewegoapi.repository.UserDetailsRepository;
import io.netty.util.internal.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    TeamRepository teamRepository;

    public void updateFavoriteTeam(String userId, Integer teamId) throws ForwardException {
        if (validateTeamId(teamId)) {
            updateUserDetails(userId, teamId);
        }
    }

    private boolean validateTeamId(Integer teamId) throws ForwardException {
        Optional<Team>optional = Optional.ofNullable(teamRepository.findByTeamId(teamId));

        Team team = optional.orElseThrow(()->{
            LOGGER.debug("Cannot found team by teamId {}",teamId);
            return new ForwardException(ErrorCode.RC400000,"Invalid Team");
        });
        LOGGER.debug("Team Name : {}",team.getTeamName());

        return true;
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
        userDetails.setFavorites(manageDuplicatedTeamId(userDetails.getFavorites(), teamId));
        userDetailsRepository.save(userDetails);
    }

    private String manageDuplicatedTeamId(String favoriteTeamList, Integer teamId) {
        List<Integer> teamList = Arrays.stream(favoriteTeamList.split(",")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        if (!teamList.contains(teamId)) {
            favoriteTeamList += "," + teamId.toString();
        }
        return favoriteTeamList;
    }

    public List<TeamInfoVO> getTeamList() throws ForwardException{
        List<Team> teamList = teamRepository.findLatestTeamInfoList();

        //TODO:  Team table 구조 생각 > league name을 넣어야할까?
        if (!ObjectUtils.isEmpty(teamList)) {
            List<TeamInfoVO> teamInfoVOList = new ArrayList<>();
            teamList.forEach(team -> {
                teamInfoVOList.add(TeamInfoVO.builder()
                        .teamId(team.getTeamId())
                        .teamName(team.getTeamName())
                        .league(Integer.toString(team.getLeagueId()))
                        .icon(team.getLogo())
                        .build());
            });

            return teamInfoVOList;
        }

        LOGGER.error("Team List Not Found(size: {})", teamList.size());
        throw new ForwardException(ErrorCode.RC401000);
    }
}
