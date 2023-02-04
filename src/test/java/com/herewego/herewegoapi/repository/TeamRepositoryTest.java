package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.common.Consts;
import com.herewego.herewegoapi.model.entity.Team;
import com.herewego.herewegoapi.model.entity.UserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamRepositoryTest {
    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
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
    }

    @AfterEach
    public void teardown() {
        teamRepository.deleteByTeamId(Consts.TEAMID1);
        teamRepository.deleteByTeamId(Consts.TEAMID2);
    }

    @Test
    void findLatestTeamInfoList() {
        List<Team> teamList = teamRepository.findLatestTeamInfoList();
        Assertions.assertEquals(2, teamList.size());
    }
}