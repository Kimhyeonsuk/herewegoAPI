package com.herewego.herewegoapi.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserVO {
    Integer homeTeamId;

    List<FavoriteTeamVO> favorites;

    List<Integer> gameUnit;
}
