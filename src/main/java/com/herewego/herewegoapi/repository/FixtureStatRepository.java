package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.model.entity.FixtureStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FixtureStatRepository extends JpaRepository<FixtureStatistics, Long> {

    @Query(value = "SELECT * FROM FixtureStatistics " +
            "WHERE team_id=:teamId ORDER BY timestamp DESC LIMIT :limit", nativeQuery = true)
    List<FixtureStatistics> getFixtureStatisticsList(@Param("teamId") Integer teamId, @Param("limit") Integer limit);
}
