package com.herewego.herewegoapi.service;

import com.herewego.herewegoapi.common.Consts;
import com.herewego.herewegoapi.model.entity.League;
import com.herewego.herewegoapi.model.entity.Team;
import com.herewego.herewegoapi.model.entity.UserDetails;
import com.herewego.herewegoapi.model.response.FavoriteTeamVO;
import com.herewego.herewegoapi.model.response.UserVO;
import com.herewego.herewegoapi.repository.LeagueRepository;
import com.herewego.herewegoapi.repository.TeamRepository;
import com.herewego.herewegoapi.repository.UserDetailsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @BeforeEach
    public void setup() {
        userDetailsRepository.save(UserDetails.builder()
                .userId(Consts.USERID)
                .favorites(Consts.FAVORITETEAM)
                .build());

        teamRepository.save(Team.builder()
                .teamId(Consts.TEAMID1)
                .leagueId(Consts.LEAGUEID)
                .season(Consts.SEASON)
                .teamName(Consts.TEAMNAME1)
                .logo(Consts.LOGOURL)
                .build());

        teamRepository.save(Team.builder()
                .teamId(Consts.TEAMID2)
                .leagueId(Consts.LEAGUEID)
                .season(Consts.SEASON)
                .teamName(Consts.TEAMNAME2)
                .logo(Consts.LOGOURL)
                .build());
        leagueRepository.save(League.builder()
                .leagueId(Consts.LEAGUEID)
                .leagueName(Consts.LEAGUENAME)
                .logo(Consts.LOGOURL).build());
    }

    @AfterEach
    public void teardown() {
        userDetailsRepository.deleteByUserId(Consts.USERID);
        teamRepository.deleteByTeamId(Consts.TEAMID1);
        teamRepository.deleteByTeamId(Consts.TEAMID2);
        leagueRepository.deleteByLeagueId(Consts.LEAGUEID);
    }

    @Test
    public void getUserVOTest() {
        //UserVO userVO = userService.getUserInformation("sukrrard97@gmail.com");

        //System.out.println("test");
    }

    @Test
    public void getFavoriteTeamIdTest() {
        List<Integer> favoriteTeamIdList = userService.getFavoritesTeamId(Consts.USERID);

        Assertions.assertNotNull(favoriteTeamIdList);
        Assertions.assertEquals(2,favoriteTeamIdList.size());
    }

    @Test
    public void createFavoriteListTest() {
        List<FavoriteTeamVO> favoriteTeamVOList = userService.createFavoriteList(Consts.USERID);

        Assertions.assertNotNull(favoriteTeamVOList);
        Assertions.assertEquals(2,favoriteTeamVOList.size());
    }
}