package com.herewego.herewegoapi.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TeamInfoVO {
    String teamName;

    String league;

    String icon;

    Integer table;

    List<LeagueVO> joining;

    StatisticsVO statistics;
}
