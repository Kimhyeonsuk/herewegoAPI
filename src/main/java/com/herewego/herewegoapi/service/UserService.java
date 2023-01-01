package com.herewego.herewegoapi.service;

import com.herewego.herewegoapi.common.Utils;
import com.herewego.herewegoapi.model.entity.*;
import com.herewego.herewegoapi.model.response.*;
import com.herewego.herewegoapi.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorizationRepository authorizationRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    FixtureStatRepository fixtureStatRepository;

    public UserVO getUserInformation(String email, String accountToken, String accountTokenParam) {
        if (ObjectUtils.isEmpty(accountToken)) {
            accountToken = accountTokenParam;
        }
        if (ObjectUtils.isEmpty(email)) {
            Optional<Authorization> authorizationOptional = Optional.ofNullable(authorizationRepository.findByAccessToken(accountToken));
            Authorization authorization = authorizationOptional.orElseGet(()->Authorization.builder().build());
            email = authorization.getEmail();
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        User user = userOptional.orElseGet(()->User.builder().build());
        LOGGER.debug("User Email : {} ", user.getEmail());

        //등록된 home team 유무에 따른 분기
        if (!ObjectUtils.isEmpty(user.getTeamId())) {
            return UserVO.builder()
                    .email(email)
                    .accessToken(accountToken)
                    .favorites(createFavoriteList())
                    .homeTeam(createTeamInfo(user.getTeamId(), Utils.gameUnitStringToList(user.getGameUnit())))
                    .gameUnit(Utils.gameUnitStringToList(user.getGameUnit()))
                    .build();
        }
        else {
            return UserVO.builder()
                    .email(email)
                    .accessToken(accountToken)
                    .favorites(createFavoriteList())
                    .gameUnit(Utils.gameUnitStringToList(user.getGameUnit()))
                    .build();
        }
    }


    //TODO : favorite team list create
    //현재는 임의로 특정 팀 대입중
    private List<FavoriteTeamVO> createFavoriteList() {
        List<FavoriteTeamVO> favoriteTeamVOList = new ArrayList<>();

        Optional<Team> optionalTeam = Optional.ofNullable(teamRepository.findByTeamId(40));
        optionalTeam.ifPresent(team -> {
            favoriteTeamVOList.add(FavoriteTeamVO.builder()
                            .teamName(team.getTeamName())
                            .league("English Preamier League")
                            .icon(team.getLogo())
                            .rank(1)
                            .build());
        });

        return favoriteTeamVOList;
    }


    //TODO Team 과 league간의 관계를 맺은 후 vo 재성성, 현재는 임의의 리그를 joining league로 규정 짓고 대입중
    private TeamInfoVO createTeamInfo(int homeTeamId, List<Integer> gameUnit) {
        TeamInfoVO teamInfoVO = new TeamInfoVO();

        Optional<Team> optionalTeam = Optional.ofNullable(teamRepository.findByTeamId(homeTeamId));
        optionalTeam.ifPresent(team -> {
            teamInfoVO.setTeamName(team.getTeamName());
            teamInfoVO.setIcon(team.getLogo());
        });

        //임의 값 넣는 부분
        Optional<League> optionalLeague = Optional.ofNullable(leagueRepository.findByLeagueId(39));
        optionalLeague.ifPresent(league -> {
            teamInfoVO.setLeague(league.getLeagueName());
            List<LeagueVO> joining = new ArrayList<>();
            joining.add( LeagueVO.builder()
                    .leagueName(league.getLeagueName())
                    .icon(league.getLogo())
                    .build());

            teamInfoVO.setJoining(joining);
        });
        teamInfoVO.setTable(1);


        //statistics create
        teamInfoVO.setStatistics(createStatistics(homeTeamId, gameUnit));

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
