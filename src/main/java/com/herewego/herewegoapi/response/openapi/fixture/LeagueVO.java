package com.herewego.herewegoapi.response.openapi.fixture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueVO {
    Integer id;

    String name;

    String country;

    String logo;

    String flag;

    Integer season;

    String round; // Regular Season - 1
}
