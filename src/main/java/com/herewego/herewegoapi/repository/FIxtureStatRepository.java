package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.model.entity.FixtureStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FIxtureStatRepository extends JpaRepository<FixtureStatistics, Long> {
}
