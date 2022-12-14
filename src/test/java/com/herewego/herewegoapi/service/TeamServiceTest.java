package com.herewego.herewegoapi.service;

import com.herewego.herewegoapi.common.Consts;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.entity.Team;
import com.herewego.herewegoapi.model.entity.UserDetails;
import com.herewego.herewegoapi.repository.TeamRepository;
import com.herewego.herewegoapi.repository.UserDetailsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TeamServiceTest {
    @Autowired
    TeamService teamService;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
        userDetailsRepository.save(UserDetails.builder()
                .email(Consts.EMAIL)
                .authProvider(Consts.AUTHPROVIDER)
                .role(Consts.ROLE)
                .favorites(Consts.FAVORITETEAM2)
                .build());

        teamRepository.save(Team.builder()
                .teamId(Consts.TEAMID1)
                .teamName(Consts.TEAMNAME1)
                .logo(Consts.LOGOURL)
                .build());

        teamRepository.save(Team.builder()
                .teamId(Consts.TEAMID2)
                .teamName(Consts.TEAMNAME2)
                .logo(Consts.LOGOURL)
                .build());
    }
    @AfterEach
    public void teardown() {
        userDetailsRepository.deleteByEmailAndAuthProvider(Consts.EMAIL, Consts.AUTHPROVIDER);
        teamRepository.deleteByTeamId(Consts.TEAMID1);
        teamRepository.deleteByTeamId(Consts.TEAMID2);
    }


    @Test
    void updateFavoriteTeamTest_success() throws ForwardException {
        teamService.updateFavoriteTeam(Consts.EMAIL, Consts.TEAMNAME2, Consts.LEAGUENAME);

        UserDetails userDetails = userDetailsRepository.findByEmail(Consts.EMAIL);
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(userDetails.getFavorites(), Consts.FAVORITETEAM);
    }
}