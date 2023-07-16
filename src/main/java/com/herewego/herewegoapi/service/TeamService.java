package com.herewego.herewegoapi.service;

import com.herewego.herewegoapi.common.Utils;
import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.entity.*;
import com.herewego.herewegoapi.model.response.LeagueVO;
import com.herewego.herewegoapi.model.response.StatisticsVO;
import com.herewego.herewegoapi.model.response.TeamInfoVO;
import com.herewego.herewegoapi.repository.*;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    FixtureStatRepository fixtureStatRepository;

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

    public String manageDuplicatedTeamId(String favoriteTeamList, Integer teamId) {
        if (ObjectUtils.isEmpty(favoriteTeamList)) {
            return teamId.toString();
        }
        List<Integer> teamList = Arrays.stream(favoriteTeamList.split(",")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        if (!teamList.contains(teamId)) {
            favoriteTeamList += "," + teamId.toString();
        }
        else {
            favoriteTeamList = deleteTeamFromFavoriteTeamList(teamList, teamId);
        }
        return favoriteTeamList;
    }

    public String deleteTeamFromFavoriteTeamList(List<Integer> teamList, Integer deletedTeamId) {
        List<Integer> newTeamList = teamList.stream().filter(teamId -> !teamId.equals(deletedTeamId)).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        if (newTeamList.size()==0) {
            return "";
        }
        for (Integer t : newTeamList) {
            sb.append(t);
            sb.append(",");
        }

        return sb.substring(0, sb.length()-1).toString();
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

    public TeamInfoVO getTeamDetails(String userId, Integer teamId) throws ForwardException{
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (!userOptional.isPresent()) {
            throw new ForwardException(ErrorCode.RC401000);
        }
        LOGGER.debug("Game Unit : {}", userOptional.get().getGameUnit());

        Team team = teamRepository.findLatestTeamInfo(teamId);
        if (ObjectUtils.isEmpty(team))
            throw new ForwardException(ErrorCode.RC401000);
        LOGGER.debug("Team ID: {}", team.getTeamName());


        League league = leagueRepository.findByLeagueId(team.getLeagueId());
        if(ObjectUtils.isEmpty(league))
            throw new ForwardException(ErrorCode.RC401000);
        LOGGER.debug("League name : {}", league.getLeagueName());

        TeamInfoVO teamInfoVO = new TeamInfoVO().builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .league(league.getLeagueName())
                .icon(team.getLogo())
                .joining(new ArrayList<>(Arrays.asList(LeagueVO.builder()
                        .leagueName(league.getLeagueName())
                        .icon(league.getLogo()).build())))
                .statistics(createStatistics(team.getTeamId(), Utils.gameUnitStringToList(userOptional.get().getGameUnit())))
                .build();
        return teamInfoVO;
    }

    private StatisticsVO createStatistics(int teamId, List<Integer> gameUnit) {
        Optional<List<FixtureStatistics>> optionalFixtureStatistics =
                Optional.ofNullable(fixtureStatRepository.getFixtureStatisticsList(teamId, gameUnit.get(gameUnit.size()-1)));

        if (optionalFixtureStatistics.isPresent()) {
            return new StatisticsVO(optionalFixtureStatistics.get());
        }

        return null;
    }
}
