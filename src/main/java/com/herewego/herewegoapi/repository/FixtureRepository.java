package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.model.entity.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Long> {
    Fixture findByFixtureId(Integer fixtureId);
}
