package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

    League findByLeagueId(Integer leagueId);

    @Transactional
    void deleteByLeagueId(Integer leagueId);
}
