package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TeamRepository extends JpaRepository <Team, Long> {

    Team findByTeamId(Integer teamId);

    @Transactional
    void deleteByTeamId(Integer teamId);
}
