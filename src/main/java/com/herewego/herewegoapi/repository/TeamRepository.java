package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository <Team, Long> {

    Team findByTeamId(Integer teamId);

    Team findByTeamName(String teamName);

    @Transactional
    void deleteByTeamId(Integer teamId);

//    @Query("SELECT t.id, t.teamId, t.leagueId, t.season, t.team_name, t.logo FROM " +
//            "(SELECT id, teamId, leagueId, season, team_name, logo, " +
//            "RANK() OVER (PARTITION BY teamId ORDER BY season DESC) as rank " +
//            "FROM Team) as t " +
//            "WHERE t.rank = 1")
//@Query("SELECT team.id, team.teamId, team.leagueId, team.season, team.teamName, team.logo, team.createdDate, team.updatedDate FROM " +
//        "(SELECT t.id, t.teamId, t.leagueId, t.season, t.teamName, t.logo, t.createdDate, t.updatedDate, " +
//        "RANK() OVER (PARTITION BY t.teamId ORDER BY t.season DESC) as rank " +
//        "FROM Team t) as team " +
//        "WHERE team.rank = 1")
@Query(value = "SELECT T.id, T.team_id, T.league_id, T.season, T.team_name, T.logo, T.created_date, T.updated_date " +
        "FROM Team T " +
        "INNER JOIN " +
        "(SELECT t.id, t.team_id, t.league_id, t.season, t.team_name, t.logo, t.created_date, t.updated_date, " +
        "RANK() OVER (PARTITION BY t.team_id ORDER BY t.season DESC) as rnk " +
        "FROM Team t) subTeam " +
        "ON T.id = subTeam.id " +
        "WHERE subTeam.rnk = 1", nativeQuery = true)
    List<Team> findLatestTeamInfoList();

@Query(value = "SELECT * FROM Team " +
        "WHERE team_id=:teamId " +
        "ORDER BY season " +
        "DESC LIMIT 1", nativeQuery = true)
    Team findLatestTeamInfo(@Param("teamId") Integer teamId);


}
