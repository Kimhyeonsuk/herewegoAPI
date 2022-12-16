package com.herewego.herewegoapi.response.openapi.fixture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixtureResponseVO {
    FixtureVO fixture;

    LeagueVO league;

    Teams teams;

    Scores score;

    List<Object> statistics;
}
